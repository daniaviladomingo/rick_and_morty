package avila.daniel.rickmorty.ui.activity.charactersfrom

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import avila.daniel.repository.cache.model.compose.CharacterFilter
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import avila.daniel.rickmorty.di.qualifiers.SearchFilterCharacters
import avila.daniel.rickmorty.ui.activity.character.CharacterActivity
import avila.daniel.rickmorty.ui.fragment.characters.CharactersAdapter
import avila.daniel.rickmorty.ui.model.CharactersSource
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.activity_characters_from.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFromActivity : BaseActivity() {

    private val charactersFromViewModel: CharactersFromViewModel by viewModel()

    private lateinit var charactersSource: CharactersSource

    private var adapter = CharactersAdapter(inject<() -> CharacterFilter>(
        SearchFilterCharacters
    ).value)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setListener()

        adapter.onClickListener = {
            charactersFromViewModel.openCharacterDetail(it)
        }

        list_characters.adapter = adapter

        intent.extras?.run {
            charactersSource = getParcelable(CHARACTERS_SOURCE)!!
            when (charactersSource) {
                CharactersSource.FAVORITES -> {
                    charactersFromViewModel.loadCharactersFromFavorite()
                }
                CharactersSource.LOCATION -> {
                    supportActionBar?.setSubtitle(R.string.action_bar_subtitle_location)
                    charactersFromViewModel.loadCharactersFromLocation(getInt(ID))
                }
                CharactersSource.EPISODE -> {
                    supportActionBar?.setSubtitle(R.string.action_bar_subtitle_episode)
                    charactersFromViewModel.loadCharactersFromEpisode(getInt(ID))
                }
            }

            supportActionBar?.title = getString(TITLE)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        if (charactersSource == CharactersSource.FAVORITES) {
            charactersFromViewModel.loadCharactersFromFavorite()
        }
    }

    private fun setListener() {
        charactersFromViewModel.charactersLiveData.observe(this, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        adapter.setData(this)
                        no_favorite.visibility = if (this.isEmpty()) View.VISIBLE else View.GONE
                    }
                }
            }
        })

        charactersFromViewModel.characterParcelabeLiveData.observe(
            this,
            Observer
            { resource ->
                resource?.run {
                    managementResourceState(status, message)
                    if (status == ResourceState.SUCCESS) {
                        data?.let {
                            startActivity(
                                Intent(
                                    this@CharactersFromActivity,
                                    CharacterActivity::class.java
                                ).apply {
                                    putExtra(CharacterActivity.CHARACTER, it)
                                })
                        }
                    }
                }
            })
    }

    override fun getLayoutId(): Int = R.layout.activity_characters_from

    override fun checkAgain(): () -> Unit = {}

    override fun tryAgain(): () -> Unit = {}

    companion object {
        const val ID = "id"
        const val CHARACTERS_SOURCE = "characters.source"
        const val TITLE = "title"
    }
}

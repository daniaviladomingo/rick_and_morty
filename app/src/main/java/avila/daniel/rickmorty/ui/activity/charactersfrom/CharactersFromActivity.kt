package avila.daniel.rickmorty.ui.activity.charactersfrom

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import avila.daniel.rickmorty.databinding.ActivityCharactersFromBinding
import avila.daniel.rickmorty.ui.activity.character.CharacterDetailActivity
import avila.daniel.rickmorty.ui.fragment.characters.CharactersAdapter
import avila.daniel.rickmorty.ui.model.CharactersSource
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.activity_characters_from.*
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFromActivity : BaseActivity() {

    private val charactersFromViewModel: CharactersFromViewModel by viewModel()

    private lateinit var charactersSource: CharactersSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (binding as ActivityCharactersFromBinding).viewModel = charactersFromViewModel

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setListener()

        list_characters.adapter = CharactersAdapter(charactersFromViewModel)

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
        charactersFromViewModel.charactersLiveData.observe(
            this,
            Observer { resource -> resource?.run { managementResourceState(status, message) } })

        charactersFromViewModel.characterParcelableLiveData.observe(
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
                                    CharacterDetailActivity::class.java
                                ).apply {
                                    putExtra(CharacterDetailActivity.CHARACTER, it)
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

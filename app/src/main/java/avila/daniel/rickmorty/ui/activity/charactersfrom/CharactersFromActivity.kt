package avila.daniel.rickmorty.ui.activity.charactersfrom

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.databinding.ActivityCharactersFromBinding
import avila.daniel.rickmorty.ui.activity.character.CharacterDetailActivity
import avila.daniel.rickmorty.ui.fragment.characters.CharactersAdapter
import avila.daniel.rickmorty.ui.model.CharactersSource
import kotlinx.android.synthetic.main.activity_characters_from.*
import org.koin.android.viewmodel.ext.android.viewModel

class CharactersFromActivity : BaseActivity() {

    private val charactersFromViewModel: CharactersFromViewModel by viewModel()

    private var isFavoriteSource = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (binding as ActivityCharactersFromBinding).viewModel = charactersFromViewModel

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        list_characters.adapter = CharactersAdapter(charactersFromViewModel)

        charactersFromViewModel.characterParcelableLiveData.observe(
            this,
            Observer
            { resource ->
                startActivity(
                    Intent(
                        this@CharactersFromActivity,
                        CharacterDetailActivity::class.java
                    ).apply {
                        putExtra(CharacterDetailActivity.CHARACTER, resource)
                    })
            })

        intent.extras?.run {
            when (getParcelable<CharactersSource>(CHARACTERS_SOURCE)) {
                CharactersSource.FAVORITES -> {
                    isFavoriteSource = true
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

    override fun onResume() {
        super.onResume()
        if (isFavoriteSource) {
            charactersFromViewModel.loadCharactersFromFavorite()
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_characters_from

    override fun checkAgain(): () -> Unit = {}

    override fun tryAgain(): () -> Unit = {}

    override fun vm(): BaseViewModel = charactersFromViewModel

    companion object {
        const val ID = "id"
        const val CHARACTERS_SOURCE = "characters.source"
        const val TITLE = "title"
    }
}

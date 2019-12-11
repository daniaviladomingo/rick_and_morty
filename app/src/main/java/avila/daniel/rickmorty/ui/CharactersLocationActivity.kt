package avila.daniel.rickmorty.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.lifecycle.Observer
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import avila.daniel.rickmorty.ui.fragment.characters.CharactersAdapter
import avila.daniel.rickmorty.ui.model.CharacterUI
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.activity_characters.*
import org.koin.android.viewmodel.ext.android.viewModel

const val ID = "id"
const val CHARACTERS_SOURCE = "characters.source"
const val TITLE = "title"


class CharactersLocationActivity : BaseActivity() {

    private val charactersLocationViewModel: CharactersLocationViewModel by viewModel()

    private val characterList = mutableListOf<CharacterUI>()
    private val adapter = CharactersAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setListener()

        list_characters.adapter = adapter

        intent.extras?.run {
            charactersLocationViewModel.loadCharacters(
                getInt(ID), getParcelable(CHARACTERS_SOURCE)!!
            )
            supportActionBar?.title = getString(TITLE)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setListener() {
        charactersLocationViewModel.charactersLiveData.observe(this, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        characterList.clear()
                        characterList.addAll(this)
                        adapter.update(characterList)
                    }
                }
            }
        })
    }

    override fun getLayoutId(): Int = R.layout.activity_characters

    override fun checkAgain(): () -> Unit = {}

    override fun tryAgain(): () -> Unit = {}
}

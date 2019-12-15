package avila.daniel.rickmorty.ui.activity.character

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import avila.daniel.domain.model.Character
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.activity_character.*
import org.koin.android.viewmodel.ext.android.viewModel

class CharacterActivity : BaseActivity() {
    private val characterViewModel: CharacterViewModel by viewModel()

    private lateinit var character: Character

    private lateinit var menu: Menu

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setListener()

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.extras?.run {
            characterViewModel.getCharacter(getParcelable(CHARACTER)!!)

        }
    }

    private fun setListener() {
        characterViewModel.characterLiveData.observe(this, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        supportActionBar?.title = this.name
                        character = this
                    }
                }
            }
        })

        characterViewModel.favoriteLiveData.observe(this, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        menuItem(this)
                        Toast.makeText(
                            this@CharacterActivity,
                            if (this) R.string.favorite_added else R.string.favorite_removed,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })

        characterViewModel.isFavoriteLiveData.observe(this, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        menuItem(this)
                    }
                }
            }
        })
    }

    private fun menuItem(favorite: Boolean) {
        if (favorite) {
            menu.findItem(R.id.action_favorite).isVisible = false
            menu.findItem(R.id.action_unfavorite).isVisible = true
        } else {
            menu.findItem(R.id.action_favorite).isVisible = true
            menu.findItem(R.id.action_unfavorite).isVisible = false
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.action_favorite -> {
                characterViewModel.addFavorite(character)
                true
            }
            R.id.action_unfavorite -> {
                characterViewModel.removeFavorite(character.id)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu_favorite, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun getLayoutId(): Int = R.layout.activity_character

    override fun checkAgain(): () -> Unit = {}

    override fun tryAgain(): () -> Unit = {}

    companion object {
        const val CHARACTER = "character"
    }
}
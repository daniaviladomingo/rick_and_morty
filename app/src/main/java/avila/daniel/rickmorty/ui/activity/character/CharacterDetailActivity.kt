package avila.daniel.rickmorty.ui.activity.character

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.Observer
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import avila.daniel.rickmorty.databinding.ActivityCharacterBinding
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.activity_character.*
import org.koin.android.viewmodel.ext.android.viewModel

class CharacterDetailActivity : BaseActivity() {
    private val characterDetailViewModel: CharacterDetailViewModel by viewModel()

    private var favorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (binding as ActivityCharacterBinding).viewModel = characterDetailViewModel

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setListener()

        intent.extras?.run {
            characterDetailViewModel.getCharacter(getParcelable(CHARACTER)!!)
        }
    }

    private fun setListener() {
        characterDetailViewModel.characterLiveData.observe(
            this,
            Observer { resource -> resource?.run { managementResourceState(status, message) } })

        characterDetailViewModel.favoriteLiveData.observe(this, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data!!.run {
                        favorite = this
                        invalidateOptionsMenu()
                        Toast.makeText(
                            this@CharacterDetailActivity,
                            if (this) R.string.favorite_added else R.string.favorite_removed,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })

        characterDetailViewModel.isFavoriteLiveData.observe(this, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data!!.run {
                        favorite = this
                        invalidateOptionsMenu()
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.action_favorite -> {
                characterDetailViewModel.addFavorite()
                true
            }
            R.id.action_unfavorite -> {
                characterDetailViewModel.removeFavorite()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(if (favorite) R.menu.menu_favorite else R.menu.menu_unfavorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun getLayoutId(): Int = R.layout.activity_character

    override fun checkAgain(): () -> Unit = {}

    override fun tryAgain(): () -> Unit = {}

    companion object {
        const val CHARACTER = "character"
    }
}

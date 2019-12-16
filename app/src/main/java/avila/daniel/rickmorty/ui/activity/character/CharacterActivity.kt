package avila.daniel.rickmorty.ui.activity.character

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.compose.Status
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import avila.daniel.rickmorty.ui.util.data.ResourceState
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_character.*
import org.koin.android.viewmodel.ext.android.viewModel

class CharacterActivity : BaseActivity() {
    private val characterViewModel: CharacterViewModel by viewModel()

    private lateinit var character: Character

    private var favorite = false

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
                        draw(this)
                    }
                }
            }
        })

        characterViewModel.favoriteLiveData.observe(this, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        favorite = this
                        invalidateOptionsMenu()
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
                        favorite = this
                        invalidateOptionsMenu()
                    }
                }
            }
        })
    }

    private fun draw(character: Character) {
        character.run {
            Glide.with(image_view).load(image).into(image_view)
            name_info.text = this.name
            species_info.text = this.species
            this.origin.name.run {
                if (this == "unknown") {
                    origin_unknow.visibility = View.VISIBLE
                    origin_info.visibility = View.GONE
                } else {
                    origin_unknow.visibility = View.GONE
                    origin_info.text = this
                }
            }

            this.location.name.run {
                if (this == "unknown") {
                    location_unknow.visibility = View.VISIBLE
                    location_info.visibility = View.GONE
                } else {
                    location_unknow.visibility = View.GONE
                    location_info.text = this
                }
            }

            icon_status.setImageResource(
                when (this.status) {
                    Status.ALIVE -> R.drawable.ic_alive
                    Status.DEAD -> R.drawable.ic_dead
                    else -> R.drawable.ic_unknown
                }
            )
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

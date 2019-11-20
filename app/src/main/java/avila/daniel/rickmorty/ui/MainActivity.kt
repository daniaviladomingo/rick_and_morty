package avila.daniel.rickmorty.ui

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import avila.daniel.rickmorty.ui.characters.CharactersFragment
import avila.daniel.rickmorty.ui.episodes.EpisodesFragment
import avila.daniel.rickmorty.ui.locations.LocationsFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity() {

    private val lifecycleObserver: Unit by inject { parametersOf(this.lifecycle) }

    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleObserver.run { }

        viewpager.run {
            adapter = CustomPagerAdapter(
                listOf(
                    CharactersFragment.newInstance(),
                    LocationsFragment.newInstance(),
                    EpisodesFragment.newInstance()
                ), supportFragmentManager
            )
        }

        tabs.setupWithViewPager(viewpager)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        searchView = menu.findItem(R.id.action_search).actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return true
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

//    override fun onBackPressed() {
//        if (searchView.isShown) {
//            searchView.)
//        } else {
//            super.onBackPressed()
//        }
//    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun checkAgain(): () -> Unit = {}

    override fun tryAgain(): () -> Unit = {}
}

package avila.daniel.rickmorty.ui

import android.os.Bundle
import android.view.Menu
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import avila.daniel.rickmorty.ui.characters.CharactersFragment
import avila.daniel.rickmorty.ui.episodes.EpisodesFragment
import avila.daniel.rickmorty.ui.locations.LocationsFragment
import com.ferfalk.simplesearchview.SimpleSearchView
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf

class MainActivity : BaseActivity() {

    private val lifecycleObserver: Unit by inject { parametersOf(this.lifecycle) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleObserver.run { }

        setSupportActionBar(toolbar)

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

        search_view.tabLayout = tabs
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        search_view.setMenuItem(menu.findItem(R.id.action_search))

        search_view.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                return false
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        if (search_view.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun checkAgain(): () -> Unit = {}

    override fun tryAgain(): () -> Unit = {}
}

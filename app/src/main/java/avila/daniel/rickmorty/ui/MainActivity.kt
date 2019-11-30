package avila.daniel.rickmorty.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import avila.daniel.rickmorty.ui.fragment.characters.CharactersFragment
import avila.daniel.rickmorty.ui.fragment.episodes.EpisodesFragment
import avila.daniel.rickmorty.ui.fragment.locations.LocationsFragment
import avila.daniel.searchview_test.SimpleSearchView
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // Handle action bar item clicks here. The action bar will
        return when (item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, SettingsActivity::class.java))
                true
            }
            R.id.action_info -> {
                Toast.makeText(this, "Info", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_toolbar, menu)

        search_view.setMenuItem(menu.findItem(R.id.action_search))
        search_view.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                Log.d("fff", query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.d("fff", "cambio")
//                fragmentTabs[viewpager.currentItem].updateCriteria(newText)
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

    inner class CustomPagerAdapter(
        private val fragmentList: List<Fragment>,
        fragmentManager: FragmentManager
    ) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
        override fun getItem(position: Int): Fragment = fragmentList[position]
        override fun getCount(): Int = fragmentList.size
        override fun getPageTitle(position: Int): CharSequence = when (position) {
            0 -> "CHARACTERS"
            1 -> "LOCATIONS"
            2 -> "EPISODES"
            else -> "UNKNOWN"
        }
    }
}



package avila.daniel.rickmorty.ui

import android.os.Bundle
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import avila.daniel.rickmorty.ui.characters.CharactersFragment
import avila.daniel.rickmorty.ui.episodes.EpisodesFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//
//            override fun onTabSelected(tab: TabLayout.Tab) {
//                viewpager.currentItem = tab.position
//            }
//        })

        viewpager.run {
            adapter = CustomPagerAdapter(
                listOf(
                    CharactersFragment.newInstance()
//                    EpisodesFragment.newInstance(),
//                    EpisodesFragment.newInstance()
                ), supportFragmentManager
            )
        }

        tabs.setupWithViewPager(viewpager)
    }

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun checkAgain(): () -> Unit = {}

    override fun tryAgain(): () -> Unit = {}
}

package avila.daniel.rickmorty.ui.fragment.episodes

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.InitialLoadFragment
import avila.daniel.rickmorty.ui.activity.charactersfrom.CharactersFromActivity
import avila.daniel.rickmorty.ui.activity.charactersfrom.CharactersFromActivity.Companion.CHARACTERS_SOURCE
import avila.daniel.rickmorty.ui.activity.charactersfrom.CharactersFromActivity.Companion.ID
import avila.daniel.rickmorty.ui.activity.charactersfrom.CharactersFromActivity.Companion.TITLE
import avila.daniel.rickmorty.ui.model.CharactersSource
import avila.daniel.rickmorty.ui.model.EpisodeUI
import avila.daniel.rickmorty.ui.util.ISearch
import avila.daniel.rickmorty.ui.util.data.ResourceState
import com.yuyang.stickyheaders.StickyLinearLayoutManager
import kotlinx.android.synthetic.main.fragment_episodes.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class EpisodesFragment : InitialLoadFragment(), ISearch {

    private val episodesViewModel: EpisodesViewModel by viewModel()

    private val episodesList = mutableListOf<Any>()
    private val adapter: EpisodesAdapter by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListener()

        adapter.onClickListener = { id, name ->
            startActivity(Intent(
                activity,
                CharactersFromActivity::class.java
            ).apply {
                putExtra(CHARACTERS_SOURCE, CharactersSource.EPISODE as Parcelable)
                putExtra(ID, id)
                putExtra(TITLE, name)
            })
        }

        list_episodes.layoutManager =
            StickyLinearLayoutManager(activity, adapter).apply { elevateHeaders(5) }
        list_episodes.adapter = adapter
        list_episodes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    (list_episodes.layoutManager as LinearLayoutManager).run {
                        if (childCount + findFirstVisibleItemPosition() >= itemCount) {
                            episodesViewModel.scrollEnd()
                        }
                    }
                }
            }
        })
    }

    private fun setListener() {
        episodesViewModel.itemsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        if (this.first) {
                            episodesList.clear()
                        }
                        managementHeaders(this.second)
                        adapter.update(episodesList)
                    }
                }
            }
        })
    }

    private fun managementHeaders(episodes: List<EpisodeUI>) {
        if (episodesList.size == 0) {
            episodesList.add(ItemHeader("${episodes[0].season}"))
        } else {
            val lastItem = episodesList[episodesList.size - 1] as EpisodeUI
            val firstItem = episodes[0]
            if (lastItem.season != firstItem.season) {
                episodesList.add(ItemHeader("${firstItem.season}"))
            }
        }

        episodes.forEachIndexed { index, _ ->
            if (index > 0) {
                val item0 = episodes[index - 1]
                val item1 = episodes[index]
                if (item0.season != item1.season) {
                    episodesList.add(ItemHeader("${item1.season}"))
                }
                episodesList.add(item1)
            } else {
                episodesList.add(episodes[0])
            }
        }
    }

    override fun initialLoad() {
        episodesViewModel.load()
    }

    override fun updateFilter(newFilter: String) {
        episodesViewModel.updateSearchFilter(newFilter)
    }

    override fun getLayoutId(): Int = R.layout.fragment_episodes

    override fun checkAgain(): () -> Unit = {
        episodesViewModel.load()
    }

    override fun tryAgain(): () -> Unit = {
        episodesViewModel.load()
    }

    companion object {
        @JvmStatic
        fun newInstance() = EpisodesFragment()
    }
}
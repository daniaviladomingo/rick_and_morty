package avila.daniel.rickmorty.ui.episodes

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.InitialLoadFragment
import avila.daniel.rickmorty.ui.model.EpisodeUI
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.fragment_episodes.*
import org.koin.android.viewmodel.ext.android.viewModel

class EpisodesFragment : InitialLoadFragment() {

    private val episodesViewModel: EpisodesViewModel by viewModel()

    private val episodesList = mutableListOf<EpisodeUI>()
    private val adapter = EpisodesAdapter(episodesList)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListener()

        list_episodes.adapter = adapter
        list_episodes.addItemDecoration(DividerItemDecoration(activity, VERTICAL))
        list_episodes.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (list_episodes.layoutManager as LinearLayoutManager).run {
                    episodesViewModel.listScrolled(
                        childCount,
                        findFirstVisibleItemPosition(),
                        itemCount
                    )
                }
            }
        })
    }

    private fun setListener() {
        episodesViewModel.episodesLiveData.observe(viewLifecycleOwner, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        episodesList.addAll(this)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    override fun initialLoad() {
        episodesViewModel.loadMoreEpisodes()
    }

    override fun getLayoutId(): Int = R.layout.fragment_episodes

    override fun checkAgain(): () -> Unit = {}

    companion object {
        @JvmStatic
        fun newInstance() = EpisodesFragment()
    }
}
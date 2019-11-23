package avila.daniel.rickmorty.ui.fragment.episodes

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.InitialLoadFragment
import avila.daniel.rickmorty.ui.CHARACTERS_SOURCE
import avila.daniel.rickmorty.ui.CharactersLocationActivity
import avila.daniel.rickmorty.ui.ID
import avila.daniel.rickmorty.ui.model.CharactersSource
import avila.daniel.rickmorty.ui.model.EpisodeUI
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.fragment_episodes.*
import org.koin.android.viewmodel.ext.android.viewModel

class EpisodesFragment : InitialLoadFragment() {

    private val episodesViewModel: EpisodesViewModel by viewModel()

    private val episodesList = mutableListOf<EpisodeUI>()
    private val adapter = EpisodesAdapter(episodesList) { id ->
        startActivity(Intent(
            activity,
            CharactersLocationActivity::class.java
        ).apply {
            Log.d("aaa", "Id: $id")
            putExtra(CHARACTERS_SOURCE, CharactersSource.EPISODE as Parcelable)
            putExtra(ID, id)
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListener()

        list_episodes.adapter = adapter
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
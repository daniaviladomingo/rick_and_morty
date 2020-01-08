package avila.daniel.rickmorty.ui.fragment.episodes

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseFragment
import avila.daniel.rickmorty.databinding.FragmentEpisodesBinding
import avila.daniel.rickmorty.ui.activity.charactersfrom.CharactersFromActivity
import avila.daniel.rickmorty.ui.activity.charactersfrom.CharactersFromActivity.Companion.CHARACTERS_SOURCE
import avila.daniel.rickmorty.ui.activity.charactersfrom.CharactersFromActivity.Companion.ID
import avila.daniel.rickmorty.ui.activity.charactersfrom.CharactersFromActivity.Companion.TITLE
import avila.daniel.rickmorty.ui.model.CharactersSource
import avila.daniel.rickmorty.ui.util.ISearchText
import com.yuyang.stickyheaders.StickyLinearLayoutManager
import kotlinx.android.synthetic.main.fragment_episodes.*
import org.koin.android.viewmodel.ext.android.viewModel

class EpisodesFragment : BaseFragment(), ISearchText {

    private val episodesViewModel: EpisodesViewModel by viewModel()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (binding as FragmentEpisodesBinding).viewModel = episodesViewModel

        val adapter = EpisodesAdapter(episodesViewModel)

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

        episodesViewModel.navigateCharactersEpisodeLiveData.observe(
            viewLifecycleOwner,
            Observer { resource ->
                startActivity(Intent(
                    activity,
                    CharactersFromActivity::class.java
                ).apply {
                    putExtra(CHARACTERS_SOURCE, CharactersSource.EPISODE as Parcelable)
                    putExtra(ID, resource.first)
                    putExtra(TITLE, resource.second)
                })
            })

        episodesViewModel.load()
    }

    override fun searchText(searchText: String) {
        episodesViewModel.searchText(searchText)
    }

    override fun getLayoutId(): Int = R.layout.fragment_episodes

    override fun checkAgain(): () -> Unit = {
        episodesViewModel.load()
    }

    override fun tryAgain(): () -> Unit = {
        episodesViewModel.load()
    }

    override fun vm(): Nothing? = null

    companion object {
        @JvmStatic
        fun newInstance() = EpisodesFragment()
    }
}
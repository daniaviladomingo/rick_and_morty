package avila.daniel.rickmorty.ui.fragment.locations

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
import avila.daniel.rickmorty.ui.model.LocationUI
import avila.daniel.rickmorty.ui.util.ISearchText
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.fragment_locations.*
import org.koin.android.viewmodel.ext.android.viewModel

class LocationsFragment : InitialLoadFragment(), ISearchText {

    private val locationsViewModel: LocationsViewModel by viewModel()

    private var adapter = LocationsAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        locationsViewModel.itemsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        adapter.setData(this)
                    }
                }
            }
        })

        adapter.onClickListener = { id, name ->
            startActivity(
                Intent(
                    activity,
                    CharactersFromActivity::class.java
                ).apply {
                    putExtra(CHARACTERS_SOURCE, CharactersSource.LOCATION as Parcelable)
                    putExtra(ID, id)
                    putExtra(TITLE, name)
                })
        }

        list_locations.adapter = adapter
        list_locations.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    (list_locations.layoutManager as LinearLayoutManager).run {
                        if (childCount + findFirstVisibleItemPosition() >= itemCount) {
                            locationsViewModel.scrollEnd()
                        }
                    }
                }
            }
        })
    }
    override fun initialLoad() {
        locationsViewModel.load()
    }

    override fun searchText(searchText: String) {
        locationsViewModel.searchText(searchText)
    }

    override fun getLayoutId(): Int = R.layout.fragment_locations

    override fun checkAgain(): () -> Unit = {
        locationsViewModel.load()
    }

    override fun tryAgain(): () -> Unit = {
        locationsViewModel.load()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LocationsFragment()
    }
}
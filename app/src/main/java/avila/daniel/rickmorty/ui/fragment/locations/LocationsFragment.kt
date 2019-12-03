package avila.daniel.rickmorty.ui.fragment.locations

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.InitialLoadFragment
import avila.daniel.rickmorty.ui.CHARACTERS_SOURCE
import avila.daniel.rickmorty.ui.CharactersLocationActivity
import avila.daniel.rickmorty.ui.ID
import avila.daniel.rickmorty.ui.model.CharactersSource
import avila.daniel.rickmorty.ui.model.LocationUI
import avila.daniel.rickmorty.ui.util.ISearch
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.fragment_locations.*
import org.koin.android.viewmodel.ext.android.viewModel

class LocationsFragment : InitialLoadFragment(), ISearch {

    private val locationsViewModel: LocationsViewModel by viewModel()

    private val locationsList = mutableListOf<LocationUI>()
    private val adapter = LocationsAdapter(locationsList) { id ->
        startActivity(
            Intent(
                activity,
                CharactersLocationActivity::class.java
            ).apply {
                putExtra(CHARACTERS_SOURCE, CharactersSource.LOCATION as Parcelable)
                putExtra(ID, id)
            })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setListener()

        list_locations.adapter = adapter
        list_locations.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                (list_locations.layoutManager as LinearLayoutManager).run {
                    locationsViewModel.listScrolled(
                        childCount,
                        findFirstVisibleItemPosition(),
                        itemCount
                    )
                }
            }
        })
    }

    private fun setListener() {
        locationsViewModel.locationsLiveData.observe(viewLifecycleOwner, Observer { resource ->
            resource?.run {
                managementResourceState(status, message)
                if (status == ResourceState.SUCCESS) {
                    data?.run {
                        locationsList.addAll(this)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        })
    }

    override fun initialLoad() {
        locationsViewModel.loadMoreLocations()
    }

    override fun updateFilterText(value: String) {
        locationsViewModel.searchFilter(value)
    }

    override fun getLayoutId(): Int = R.layout.fragment_locations

    override fun checkAgain(): () -> Unit = {
        locationsViewModel.loadMoreLocations()
    }

    override fun tryAgain(): () -> Unit = {
        locationsViewModel.loadMoreLocations()
    }

    companion object {
        @JvmStatic
        fun newInstance() = LocationsFragment()
    }
}
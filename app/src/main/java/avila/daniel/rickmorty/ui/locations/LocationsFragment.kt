package avila.daniel.rickmorty.ui.locations


import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.InitialLoadFragment
import avila.daniel.rickmorty.ui.model.LocationUI
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.fragment_locations.*
import org.koin.android.viewmodel.ext.android.viewModel

class LocationsFragment : InitialLoadFragment() {

    private val locationsViewModel: LocationsViewModel by viewModel()

    private val locationsList = mutableListOf<LocationUI>()
    private val adapter = LocationsAdapter(locationsList)

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

    override fun getLayoutId(): Int = R.layout.fragment_locations

    override fun checkAgain(): () -> Unit = {}

    companion object {
        @JvmStatic
        fun newInstance() = LocationsFragment()
    }
}
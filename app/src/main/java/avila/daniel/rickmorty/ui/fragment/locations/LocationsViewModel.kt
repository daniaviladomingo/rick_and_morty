package avila.daniel.rickmorty.ui.fragment.locations

import avila.daniel.domain.interactor.GetLocationsUseCase
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.LocationUI
import avila.daniel.rickmorty.ui.model.mapper.LocationUIMapper
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.util.SingleLiveEvent

class LocationsViewModel(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val locationUIMapper: LocationUIMapper,
    private val scheduleProvider: IScheduleProvider,
    private val initialPage: Int
) : BaseViewModel() {

    val locationsLiveData = SingleLiveEvent<Resource<Pair<Boolean,List<LocationUI>>>>()

    private var isLoading = false
    private var currentPage = initialPage
    private var currentSearchFilter = ""
    private var clear = false

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (!isLoading) {
            if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
                isLoading = true
                loadMoreLocations()
            }
        }
    }

    fun loadMoreLocations() {
        locationsLiveData.value = Resource.loading()
        addDisposable(getLocationsUseCase.execute(Pair(currentSearchFilter, currentPage))
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({ locations ->
                locationsLiveData.value =
                    Resource.success(Pair(clear, locationUIMapper.map(locations)))
                clear = false
                currentPage++
                isLoading = false
            }) {
                isLoading = false
                locationsLiveData.value = Resource.error(it.localizedMessage)
            })
    }

    fun updateSearchFilter(newSearchFilter: String) {
        if (newSearchFilter != currentSearchFilter) {
            currentSearchFilter = newSearchFilter
            clearNReload()
        }
    }

    private fun clearNReload() {
        clear = true
        currentPage = initialPage
        loadMoreLocations()
    }
}
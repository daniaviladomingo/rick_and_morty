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
    private val scheduleProvider: IScheduleProvider
) : BaseViewModel() {

    private var currentPage = 1
    private var totalPages = 1

    val locationsLiveData = SingleLiveEvent<Resource<List<LocationUI>?>>()

    private var isLoading = false

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (!isLoading) {
            if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
                isLoading = true
                loadMoreLocations()
            }
        }
    }

    fun loadMoreLocations() {
        if (currentPage <= totalPages) {
            locationsLiveData.value = Resource.loading()
            addDisposable(getLocationsUseCase.execute(currentPage)
                .observeOn(scheduleProvider.ui())
                .subscribeOn(scheduleProvider.io())
                .subscribe({ locations ->
                    locationsLiveData.value =
                        Resource.success(locations.second?.run { locationUIMapper.map(this) })
                    currentPage++
                    totalPages = locations.first
                    isLoading = false
                }) {
                    isLoading = false
                    locationsLiveData.value = Resource.error(it.localizedMessage)
                })
        }
    }

    fun searchFilter(text: String) {

    }
}
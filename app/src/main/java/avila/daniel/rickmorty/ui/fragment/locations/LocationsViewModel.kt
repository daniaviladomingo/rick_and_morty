package avila.daniel.rickmorty.ui.fragment.locations

import avila.daniel.domain.interactor.GetLocationsUseCase
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.base.PaginationViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.EpisodeUI
import avila.daniel.rickmorty.ui.model.LocationUI
import avila.daniel.rickmorty.ui.model.mapper.LocationUIMapper
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.util.SingleLiveEvent

class LocationsViewModel(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val locationUIMapper: LocationUIMapper,
    scheduleProvider: IScheduleProvider,
    initialPage: Int
) : PaginationViewModel<Location,LocationUI>(scheduleProvider, initialPage) {
    override fun query(): SingleUseCaseWithParameter<Pair<String, Int>, Pair<Int, List<Location>>> = getLocationsUseCase

    override fun mapper(): Mapper<Location, LocationUI>? = locationUIMapper

//    val locationsLiveData = SingleLiveEvent<Resource<Pair<Boolean, List<LocationUI>>>>()
//
//    private var isLoading = false
//    private var currentPage = initialPage
//    private var totalPage = initialPage
//    private var currentSearchFilter = ""
//    private var clear = false
//
//    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
//        if (!isLoading) {
//            if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
//                isLoading = true
//                loadMoreLocations()
//            }
//        }
//    }
//
//    fun loadMoreLocations() {
//        if (currentPage <= totalPage) {
//            locationsLiveData.value = Resource.loading()
//            addDisposable(getLocationsUseCase.execute(Pair(currentSearchFilter, currentPage))
//                .observeOn(scheduleProvider.ui())
//                .subscribeOn(scheduleProvider.io())
//                .subscribe({ locations ->
//                    locationsLiveData.value =
//                        Resource.success(Pair(clear, locationUIMapper.map(locations.second)))
//                    clear = false
//                    currentPage++
//                    isLoading = false
//                }) {
//                    isLoading = false
//                    locationsLiveData.value = Resource.error(it.localizedMessage)
//                })
//        }
//    }
//
//    fun updateSearchFilter(newSearchFilter: String) {
//        if (newSearchFilter != currentSearchFilter) {
//            currentSearchFilter = newSearchFilter
//            clearNReload()
//        }
//    }
//
//    private fun clearNReload() {
//        clear = true
//        currentPage = initialPage
//        totalPage = initialPage
//        loadMoreLocations()
//    }
}
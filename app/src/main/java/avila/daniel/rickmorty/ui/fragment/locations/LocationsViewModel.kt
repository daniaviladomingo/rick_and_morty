package avila.daniel.rickmorty.ui.fragment.locations

import avila.daniel.domain.interactor.GetLocationsUseCase
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Location
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.base.PaginationViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.LocationUI
import avila.daniel.rickmorty.ui.model.mapper.LocationUIMapper

class LocationsViewModel(
    private val getLocationsUseCase: GetLocationsUseCase,
    private val locationUIMapper: LocationUIMapper,
    scheduleProvider: IScheduleProvider,
    initialPage: Int
) : PaginationViewModel<Location,LocationUI>(scheduleProvider, initialPage) {
    override fun query(): SingleUseCaseWithParameter<Pair<String, Int>, Pair<Int, List<Location>>> = getLocationsUseCase

    override fun mapper(): Mapper<Location, LocationUI>? = locationUIMapper
}
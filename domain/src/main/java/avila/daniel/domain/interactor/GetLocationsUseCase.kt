package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Location
import io.reactivex.Single

class GetLocationsUseCase(private val repository: IRepository) :
    SingleUseCaseWithParameter<Pair<String, Int>, List<Location>> {
    override fun execute(parameter: Pair<String, Int>): Single<List<Location>> =
        repository.getLocations(parameter.first, parameter.second)
}
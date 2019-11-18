package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCase
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Location
import io.reactivex.Single

class GetLocationsUseCase(private val repository: IRepository): SingleUseCase<List<Location>?> {
    override fun execute(): Single<List<Location>?> = repository.getLocations()
}
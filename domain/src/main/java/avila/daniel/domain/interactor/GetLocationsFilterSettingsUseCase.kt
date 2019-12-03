package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCase
import avila.daniel.domain.model.settings.LocationFilterParameter
import io.reactivex.Single

class GetLocationsFilterSettingsUseCase(private val repository: IRepository): SingleUseCase<LocationFilterParameter> {
    override fun execute(): Single<LocationFilterParameter> = repository.getLocationsFilterSettings()
}
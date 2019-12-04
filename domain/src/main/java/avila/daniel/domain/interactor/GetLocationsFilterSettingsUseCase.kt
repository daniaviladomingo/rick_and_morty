package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCase
import avila.daniel.domain.model.settings.LocationFilterSettings
import io.reactivex.Single

class GetLocationsFilterSettingsUseCase(private val repository: IRepository): SingleUseCase<LocationFilterSettings> {
    override fun execute(): Single<LocationFilterSettings> = repository.getLocationsFilterSettings()
}
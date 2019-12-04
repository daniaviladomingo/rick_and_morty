package avila.daniel.repository.cache

import avila.daniel.domain.model.settings.CharactersFilterSettings
import avila.daniel.domain.model.settings.LocationFilterSettings
import io.reactivex.Single

interface IDataCache {
    fun getCharacterFilter(): Single<CharactersFilterSettings>
    fun getLocationFilter(): Single<LocationFilterSettings>
}
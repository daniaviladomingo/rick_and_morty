package avila.daniel.repository.cache

import avila.daniel.repository.cache.model.CharactersFilterSettings
import avila.daniel.repository.cache.model.LocationFilterSettings
import io.reactivex.Single

interface IDataCache {
    fun getCharacterFilter(): Single<CharactersFilterSettings>
    fun getLocationFilter(): Single<LocationFilterSettings>
}
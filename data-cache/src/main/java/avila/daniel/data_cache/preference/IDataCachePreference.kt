package avila.daniel.data_cache.preference

import avila.daniel.repository.cache.model.CharactersFilterSettings
import avila.daniel.repository.cache.model.LocationFilterSettings
import io.reactivex.Single

interface IDataCachePreference {
    fun getCharacterFilter(): Single<CharactersFilterSettings>
    fun getLocationFilter(): Single<LocationFilterSettings>
}
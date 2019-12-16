package avila.daniel.data_cache.preference

import avila.daniel.repository.cache.model.CharactersFilterSettings
import avila.daniel.domain.model.filter.LocationFilterParameter
import io.reactivex.Single

interface IDataCachePreference {
    fun getCharacterFilter(): Single<CharactersFilterSettings>
    fun getLocationFilter(): Single<LocationFilterParameter>
}
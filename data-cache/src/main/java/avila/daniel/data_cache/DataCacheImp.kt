package avila.daniel.data_cache

import avila.daniel.data_cache.preference.IDataCachePreference
import avila.daniel.repository.cache.model.CharactersFilterSettings
import avila.daniel.repository.cache.model.LocationFilterSettings
import avila.daniel.repository.cache.IDataCache
import io.reactivex.Single

class DataCacheImp(
    private val dataCachePreference: IDataCachePreference
) : IDataCache {
    override fun getCharacterFilter(): Single<CharactersFilterSettings> = dataCachePreference.getCharacterFilter()

    override fun getLocationFilter(): Single<LocationFilterSettings> = dataCachePreference.getLocationFilter()
}
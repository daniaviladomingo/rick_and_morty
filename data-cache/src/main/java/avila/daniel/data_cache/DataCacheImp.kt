package avila.daniel.data_cache

import avila.daniel.data_cache.preference.IDataCachePreference
import avila.daniel.domain.model.settings.CharactersFilterSettings
import avila.daniel.repository.cache.IDataCache
import io.reactivex.Single

class DataCacheImp(
    private val dataCachePreference: IDataCachePreference
) : IDataCache {
    override fun getCharacterFilter(): Single<CharactersFilterSettings> = dataCachePreference.getCharacterFilter()

    override fun getLocationFilter(): Single<LocationFilterSettings> = dataCachePreference.getLocationFilter()

    override fun getEpisodeFilter(): Single<EpisodeFilterSettings> = dataCachePreference.getEpisodeFilter()
}
package avila.daniel.repository.cache.preference

import avila.daniel.domain.model.settings.CharactersFilterSettings
import avila.daniel.domain.model.settings.EpisodeFilterSettings
import avila.daniel.domain.model.settings.LocationFilterSettings
import io.reactivex.Single

interface IDataCachePreference {
    fun getCharacterFilter(): Single<CharactersFilterSettings>
    fun getLocationFilter(): Single<LocationFilterSettings>
    fun getEpisodeFilter(): Single<EpisodeFilterSettings>
}
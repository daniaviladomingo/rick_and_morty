package avila.daniel.repository.cache

import avila.daniel.domain.model.settings.CharactersFilterSettings
import io.reactivex.Single

interface IDataCache {
    fun getCharacterFilter(): Single<CharactersFilterSettings>
    fun getLocationFilter(): Single<LocationFilterSettings>
    fun getEpisodeFilter(): Single<EpisodeFilterSettings>
}
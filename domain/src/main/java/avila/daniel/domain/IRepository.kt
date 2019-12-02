package avila.daniel.domain

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import avila.daniel.domain.model.settings.CharactersFilterSettings
import avila.daniel.domain.model.settings.EpisodeFilterSettings
import avila.daniel.domain.model.settings.LocationFilterSettings
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IRepository {
    fun getCharacters(): Single<List<Character>?>
    fun getCharacter(id: Int): Single<Character>
    fun getCharactersFilterSettings(): Single<CharactersFilterSettings>

    fun getLocations(): Single<List<Location>?>
    fun getLocation(id: Int): Single<Location>
    fun getLocationCharacters(idLocation: Int): Single<List<Character>>
    fun getLocationsFilterSettings(): Single<LocationFilterSettings>

    fun getEpisodes(): Single<List<Episode>?>
    fun getEpisode(id: Int): Single<Episode>
    fun getEpisodeCharacters(idEpisode: Int): Single<List<Character>>
    fun getEpisodeFilterSettings(): Single<EpisodeFilterSettings>
}
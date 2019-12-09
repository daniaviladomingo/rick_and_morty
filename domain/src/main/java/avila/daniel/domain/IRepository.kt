package avila.daniel.domain

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import io.reactivex.Single

interface IRepository {
    fun getCharacters(searchFilter: String, page: Int): Single<List<Character>>
    fun getCharacter(id: Int): Single<Character>

    fun getLocations(searchFilter: String, page: Int): Single<List<Location>>
    fun getLocation(id: Int): Single<Location>
    fun getLocationCharacters(idLocation: Int): Single<List<Character>>

    fun getEpisodes(searchFilter: String, page: Int): Single<List<Episode>>
    fun getEpisode(id: Int): Single<Episode>
    fun getEpisodeCharacters(idEpisode: Int): Single<List<Character>>
}
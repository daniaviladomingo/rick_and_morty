package avila.daniel.domain

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import io.reactivex.Completable
import io.reactivex.Single

interface IRepository {
    fun getCharacters(searchFilter: String, page: Int): Single<Pair<Int, List<Character>>>
    fun getCharacter(id: Int): Single<Character>

    fun getLocations(searchFilter: String, page: Int): Single<Pair<Int, List<Location>>>
    fun getLocation(id: Int): Single<Location>
    fun getLocationCharacters(idLocation: Int): Single<List<Character>>

    fun getEpisodes(searchFilter: String, page: Int): Single<Pair<Int, List<Episode>>>
    fun getEpisode(id: Int): Single<Episode>
    fun getEpisodeCharacters(idEpisode: Int): Single<List<Character>>

    fun getFavoriteCharacters(): Single<List<Character>>
    fun addFavoriteCharacter(character: Character): Completable
    fun removeFavoriteCharacter(id: Int): Completable
    fun isFavorite(id: Int): Single<Boolean>
}
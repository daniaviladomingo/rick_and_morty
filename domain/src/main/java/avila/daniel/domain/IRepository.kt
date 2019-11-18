package avila.daniel.domain

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IRepository {
    fun getCharacters(): Single<List<Character>?>
    fun getCharacter(id: Int): Single<Character>
    fun getLocations(): Single<List<Location>?>
    fun getLocation(id: Int): Single<Location>
    fun getEpisodes(): Single<List<Episode>?>
    fun getEpisode(id: Int): Single<Episode>
}
package avila.daniel.repository.remote

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import avila.daniel.repository.remote.model.CharacterResponse
import avila.daniel.repository.remote.model.EpisodeResponse
import avila.daniel.repository.remote.model.LocationResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IDataRemote {

    @GET("character/?")
    fun getCharacters(@Query("page") page: Int): Single<CharacterResponse>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: Int): Single<Character>

    @GET("location/?")
    fun getLocations(@Query("page") page: Int): Single<LocationResponse>

    @GET("location/{id}")
    fun getLocation(@Path("id") id: Int): Single<Location>

    @GET("episode/?")
    fun getEpisodes(@Query("page") page: Int): Single<EpisodeResponse>

    @GET("episode/{id}")
    fun getEpisode(@Path("id") id: Int): Single<Episode>
}
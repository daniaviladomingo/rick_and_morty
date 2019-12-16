package avila.daniel.repository.remote

import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import avila.daniel.repository.remote.model.CharacterApi
import avila.daniel.repository.remote.model.CharacterResponse
import avila.daniel.repository.remote.model.EpisodeResponse
import avila.daniel.repository.remote.model.LocationResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IDataRemote {

    @GET("character/?")
    fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("status") status: String,
        @Query("species") species: String,
        @Query("type") type: String,
        @Query("gender") gender: String
    ): Single<CharacterResponse>

    @GET("character/{ids}")
    fun getCharacters(@Path("ids") ids: String): Single<List<CharacterApi>>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: Int): Single<CharacterApi>

    @GET("location/?")
    fun getLocations(
        @Query("page") page: Int, @Query("name") name: String, @Query("type") type: String, @Query(
            "dimension"
        ) dimension: String
    ): Single<LocationResponse>

    @GET("location/{id}")
    fun getLocation(@Path("id") id: Int): Single<Location>

    @GET("episode/?")
    fun getEpisodes(@Query("page") page: Int, @Query("name") name: String): Single<EpisodeResponse>

    @GET("episode/{id}")
    fun getEpisode(@Path("id") id: Int): Single<Episode>
}
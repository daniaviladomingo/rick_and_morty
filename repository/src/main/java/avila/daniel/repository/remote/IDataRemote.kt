package avila.daniel.repository.remote

import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface IDataRemote {

    @GET("character/?page={page}")
    fun getCharacters(@Path("page") page: Int): Single<List<Character>>

    @GET("character/{id}")
    fun getCharacter(@Path("id") id: Int): Single<Character>

    @GET("location/?page={page}")
    fun getLocations(@Path("page") page: Int): Single<List<Location>>

    @GET("location/{id}")
    fun getLocation(@Path("id") id: Int): Single<Location>

    @GET("episode/?page={page}")
    fun getEpisodies(@Path("page") page: Int): Single<List<Episode>>

    @GET("episode/{id}")
    fun getCEpisode(@Path("id") id: Int): Single<Episode>
}
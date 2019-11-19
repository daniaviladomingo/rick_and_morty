package avila.daniel.repository

import avila.daniel.domain.IRepository
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import avila.daniel.repository.remote.IDataRemote
import io.reactivex.Single

class RepositoryImp(
    private val dataRemote: IDataRemote,
    initialPageCharacters: Int,
    initialPageLocation: Int,
    initialPageEpisode: Int
) : IRepository {

    private var currentPageCharacters = initialPageCharacters
    private var currentPageLocations = initialPageLocation
    private var currentPagePageEpisode = initialPageEpisode

    override fun getCharacters(): Single<List<Character>?> =
        dataRemote.getCharacters(currentPageCharacters).map {
            if (currentPageCharacters <= it.info.pages) {
                currentPageCharacters++
                it.results
            }
            null
        }

    override fun getCharacter(id: Int): Single<Character> = dataRemote.getCharacter(id)

    override fun getLocations(): Single<List<Location>?> =
        dataRemote.getLocations(currentPageLocations).map {
            if (currentPageLocations <= it.info.pages) {
                currentPageLocations++
                it.results
            }
            null
        }

    override fun getLocation(id: Int): Single<Location> = dataRemote.getLocation(id)

    override fun getEpisodes(): Single<List<Episode>?> =
        dataRemote.getEpisodies(currentPagePageEpisode).map {
            if (currentPagePageEpisode <= it.info.pages) {
                currentPagePageEpisode++
                it.results
            }
            null
        }

    override fun getEpisode(id: Int): Single<Episode> = dataRemote.getEpisode(id)
}
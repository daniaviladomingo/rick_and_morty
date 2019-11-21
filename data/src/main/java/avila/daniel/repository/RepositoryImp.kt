package avila.daniel.repository

import avila.daniel.domain.ILifecycleObserver
import avila.daniel.domain.IRepository
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import avila.daniel.repository.remote.IDataRemote
import avila.daniel.repository.remote.model.mapper.EpisodeApiMapper
import io.reactivex.Single

class RepositoryImp(
    private val dataRemote: IDataRemote,
    private val initialPageCharacters: Int,
    private val initialPageLocation: Int,
    private val initialPageEpisode: Int,
    private val episodeApiMapper: EpisodeApiMapper
) : IRepository, ILifecycleObserver {

    private var currentPageCharacters = initialPageCharacters
    private var currentPageLocations = initialPageLocation
    private var currentPagePageEpisode = initialPageEpisode

    override fun getCharacters(): Single<List<Character>?> =
        dataRemote.getCharacters(currentPageCharacters).map {
            if (currentPageCharacters <= it.info.pages) {
                currentPageCharacters++
                it.results
            } else {
                null
            }
        }

    override fun getCharacter(id: Int): Single<Character> = dataRemote.getCharacter(id)

    override fun getLocations(): Single<List<Location>?> =
        dataRemote.getLocations(currentPageLocations).map {
            if (currentPageLocations <= it.info.pages) {
                currentPageLocations++
                it.results
            } else {
                null
            }
        }

    override fun getLocation(id: Int): Single<Location> = dataRemote.getLocation(id)

    override fun getEpisodes(): Single<List<Episode>?> =
        dataRemote.getEpisodes(currentPagePageEpisode).map {
            if (currentPagePageEpisode <= it.info.pages) {
                currentPagePageEpisode++
                episodeApiMapper.map(it.results)
            } else {
                null
            }
        }

    override fun getEpisode(id: Int): Single<Episode> = dataRemote.getEpisode(id)

    override fun onDestroy() {
        currentPageCharacters = initialPageCharacters
        currentPageLocations = initialPageLocation
        currentPagePageEpisode = initialPageEpisode
    }
}
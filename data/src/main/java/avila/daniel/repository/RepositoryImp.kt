package avila.daniel.repository

import avila.daniel.domain.IRepository
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import avila.daniel.domain.model.ParameterCharacter
import avila.daniel.domain.model.settings.CharactersFilterSettings
import avila.daniel.domain.model.settings.LocationFilterSettings
import avila.daniel.repository.cache.IDataCache
import avila.daniel.repository.remote.IDataRemote
import avila.daniel.repository.remote.model.mapper.CharacterApiMapper
import avila.daniel.repository.remote.model.mapper.EpisodeApiMapper
import avila.daniel.repository.remote.model.mapper.GenderParameterMapper
import avila.daniel.repository.remote.model.mapper.StatusParameterMapper
import io.reactivex.Single

class RepositoryImp(
    private val dataRemote: IDataRemote,
    private val dataCache: IDataCache,
    private val characterApiMapper: CharacterApiMapper,
    private val episodeApiMapper: EpisodeApiMapper,
    private val statusParameterMapper: StatusParameterMapper,
    private val genderParameterMapper: GenderParameterMapper
) : IRepository {

    override fun getCharacters(parameterCharacter: ParameterCharacter): Single<Pair<Int, List<Character>?>> =
        dataRemote.getCharacters(
            parameterCharacter.page,
            parameterCharacter.name,
            statusParameterMapper.map(parameterCharacter.status),
            parameterCharacter.species,
            parameterCharacter.type,
            genderParameterMapper.map(parameterCharacter.gender)
        ).map {
            Pair(
                it.info.pages,
                characterApiMapper.map(it.results)
            )
        }

    override fun getCharacter(id: Int): Single<Character> = dataRemote.getCharacter(id)

    override fun getCharactersFilterSettings(): Single<CharactersFilterSettings> =
        dataCache.getCharacterFilter()

    override fun getLocations(page: Int): Single<Pair<Int, List<Location>?>> =
        dataRemote.getLocations(page).map {
            Pair(
                it.info.pages,
                it.results
            )
        }

    override fun getLocation(id: Int): Single<Location> = dataRemote.getLocation(id)

    override fun getLocationCharacters(idLocation: Int): Single<List<Character>> =
        getLocation(idLocation).flatMap { location ->
            dataRemote.getCharacters(extractIdsCharacters(location.residents))
        }

    override fun getLocationsFilterSettings(): Single<LocationFilterSettings> =
        dataCache.getLocationFilter()

    override fun getEpisodes(page: Int): Single<Pair<Int, List<Episode>?>> =
        dataRemote.getEpisodes(page).map {
            Pair(
                it.info.pages,
                episodeApiMapper.map(it.results)
            )
        }

    override fun getEpisode(id: Int): Single<Episode> = dataRemote.getEpisode(id)

    override fun getEpisodeCharacters(idEpisode: Int): Single<List<Character>> =
        getEpisode(idEpisode).flatMap { episode ->
            dataRemote.getCharacters(extractIdsCharacters(episode.characters))
        }


    private fun extractIdsCharacters(urlCharactersList: List<String>): String {
        var ids = ""
        urlCharactersList.forEach { urlCharacter ->
            if (ids.isNotEmpty()) {
                ids += ","
            }
            ids += urlCharacter.substringAfterLast('/')
        }
        return ids
    }
}

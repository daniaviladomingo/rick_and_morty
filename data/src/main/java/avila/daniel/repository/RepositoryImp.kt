package avila.daniel.repository

import avila.daniel.domain.IRepository
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import avila.daniel.domain.model.settings.LocationFilterSettings
import avila.daniel.domain.model.settings.compose.CharacterFilterParameter
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

    override fun getCharacters(searchFilter: String, page: Int): Single<List<Character>> =
        dataCache.getCharacterFilter().flatMap { filterSettings ->

            var filterName = ""
            var filterSpecie = ""
            var filterType = ""

            when (filterSettings.parameterFilter) {
                CharacterFilterParameter.NAME -> filterName = searchFilter
                CharacterFilterParameter.SPECIES -> filterSpecie = searchFilter
                CharacterFilterParameter.TYPE -> filterType = searchFilter
            }

            dataRemote.getCharacters(
                page,
                filterName,
                statusParameterMapper.map(filterSettings.status),
                filterSpecie,
                filterType,
                genderParameterMapper.map(filterSettings.gender)
            ).map {
                characterApiMapper.map(it.results)
            }
        }

    override fun getCharacter(id: Int): Single<Character> = dataRemote.getCharacter(id)

    override fun getLocations(searchFilter: String, page: Int): Single<List<Location>> =
        dataCache.getLocationFilter().flatMap { filterSettings ->

            var filterName = ""
            var filterType = ""
            var filterDimension = ""

            when (filterSettings) {
                LocationFilterSettings.NAME -> filterName = searchFilter
                LocationFilterSettings.TYPE -> filterType = searchFilter
                LocationFilterSettings.DIMENSION -> filterDimension = searchFilter
            }

            dataRemote.getLocations(page, filterName, filterType, filterDimension).map {
                it.results
            }
        }

    override fun getLocation(id: Int): Single<Location> = dataRemote.getLocation(id)

    override fun getLocationCharacters(idLocation: Int): Single<List<Character>> =
        getLocation(idLocation).flatMap { location ->
            dataRemote.getCharacters(extractIdsCharacters(location.residents))
        }

    override fun getEpisodes(searchFilter: String, page: Int): Single<List<Episode>> =
        dataRemote.getEpisodes(page, searchFilter).map {
            episodeApiMapper.map(it.results)
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

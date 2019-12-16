package avila.daniel.repository

import avila.daniel.domain.IRepository
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.Location
import avila.daniel.repository.cache.IDataCache
import avila.daniel.domain.model.filter.LocationFilterParameter
import avila.daniel.domain.model.filter.CharacterFilterParameter
import avila.daniel.repository.remote.IDataRemote
import avila.daniel.repository.remote.model.mapper.CharacterApiMapper
import avila.daniel.repository.remote.model.mapper.EpisodeApiMapper
import avila.daniel.repository.remote.model.mapper.GenderParameterMapper
import avila.daniel.repository.remote.model.mapper.StatusParameterMapper
import io.reactivex.Completable
import io.reactivex.Single

class RepositoryImp(
    private val dataRemote: IDataRemote,
    private val dataCache: IDataCache,
    private val characterApiMapper: CharacterApiMapper,
    private val episodeApiMapper: EpisodeApiMapper,
    private val statusParameterMapper: StatusParameterMapper,
    private val genderParameterMapper: GenderParameterMapper
) : IRepository {

    override fun getCharacters(
        searchFilter: String,
        page: Int
    ): Single<Pair<Int, List<Character>>> =
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
                Pair(it.info.pages, characterApiMapper.map(it.results))
            }
        }

    override fun getCharacter(id: Int): Single<Character> =
        dataRemote.getCharacter(id).map { characterApiMapper.map(it) }

    override fun getLocations(searchFilter: String, page: Int): Single<Pair<Int, List<Location>>> =
        dataCache.getLocationFilter().flatMap { filterSettings ->

            var filterName = ""
            var filterType = ""
            var filterDimension = ""

            when (filterSettings) {
                LocationFilterParameter.NAME -> filterName = searchFilter
                LocationFilterParameter.TYPE -> filterType = searchFilter
                LocationFilterParameter.DIMENSION -> filterDimension = searchFilter
            }

            dataRemote.getLocations(page, filterName, filterType, filterDimension).map {
                Pair(it.info.pages, it.results)
            }
        }

    override fun getLocation(id: Int): Single<Location> = dataRemote.getLocation(id)

    override fun getLocationCharacters(idLocation: Int): Single<List<Character>> =
        getLocation(idLocation).flatMap { location ->
            getCharactersFrom(location.residents)
        }

    override fun getEpisodes(searchFilter: String, page: Int): Single<Pair<Int, List<Episode>>> =
        dataRemote.getEpisodes(page, searchFilter).map {
            Pair(it.info.pages, episodeApiMapper.map(it.results))
        }

    override fun getEpisode(id: Int): Single<Episode> = dataRemote.getEpisode(id)

    override fun getEpisodeCharacters(idEpisode: Int): Single<List<Character>> =
        getEpisode(idEpisode).flatMap { episode ->
            getCharactersFrom(episode.characters)
        }

    override fun getCharactersFavorites(): Single<List<Character>> =
        dataCache.getCharactersFavorites()

    override fun addCharacterFavorite(character: Character): Completable =
        dataCache.addCharacterFavorite(character)

    override fun removeCharacterFavorite(id: Int): Completable =
        dataCache.removeCharacterFavorite(id)

    override fun isFavorite(id: Int): Single<Boolean> = dataCache.isFavorite(id)

    override fun getCharacterFilter(): Single<CharacterFilterParameter> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocationFilter(): Single<LocationFilterParameter> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getCharactersFrom(urlCharactersList: List<String>): Single<List<Character>> {
        val ids = extractIdsCharacters(urlCharactersList)
        return if (ids.size > 1) {
            dataRemote.getCharacters(generateIdsParameter(ids)).map { characterApiMapper.map(it) }
        } else {
            dataRemote.getCharacter(ids[0]).map { listOf(it) }.map { characterApiMapper.map(it) }
        }
    }

    private fun extractIdsCharacters(urlCharactersList: List<String>): IntArray =
        IntArray(urlCharactersList.size).apply {
            urlCharactersList.forEachIndexed { index, urlCharacter ->
                this[index] = (urlCharacter.substringAfterLast('/').toInt())
            }
        }

    private fun generateIdsParameter(ids: IntArray): String {
        var parameterIds = ""
        ids.forEach { id ->
            if (parameterIds.isNotEmpty()) {
                parameterIds += ","
            }
            parameterIds += id
        }
        return parameterIds
    }
}

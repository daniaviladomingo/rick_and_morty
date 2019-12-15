package avila.daniel.data_cache

import avila.daniel.data_cache.db.IDataCacheDb
import avila.daniel.data_cache.preference.IDataCachePreference
import avila.daniel.domain.model.Character
import avila.daniel.repository.cache.model.CharactersFilterSettings
import avila.daniel.repository.cache.model.LocationFilterSettings
import avila.daniel.repository.cache.IDataCache
import io.reactivex.Completable
import io.reactivex.Single

class DataCacheImp(
    private val dataCachePreference: IDataCachePreference,
    private val dataCacheDb: IDataCacheDb
) : IDataCache {
    override fun getCharacterFilter(): Single<CharactersFilterSettings> = dataCachePreference.getCharacterFilter()

    override fun getLocationFilter(): Single<LocationFilterSettings> = dataCachePreference.getLocationFilter()

    override fun addCharacterFavorite(character: Character): Completable = dataCacheDb.addFavoriteCharacter(character)

    override fun getCharactersFavorites(): Single<List<Character>> = dataCacheDb.getAllFavoriteCharacters()

    override fun removeCharacterFavorite(id: Int): Completable = dataCacheDb.removeFavoriteCharacter(id)

    override fun isFavorite(id: Int): Single<Boolean> = dataCacheDb.isFavorite(id)
}
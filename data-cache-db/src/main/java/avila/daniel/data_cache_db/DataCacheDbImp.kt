package avila.daniel.data_cache_db

import avila.daniel.data_cache.db.IDataCacheDb
import avila.daniel.data_cache_db.definition.AppDatabase
import avila.daniel.data_cache_db.model.mapper.CharacterAllMapper
import avila.daniel.data_cache_db.model.mapper.CharacterDbMapper
import avila.daniel.data_cache_db.model.mapper.LocationDbMapper
import avila.daniel.data_cache_db.model.mapper.OriginDbMapper
import avila.daniel.domain.model.Character
import io.reactivex.Completable
import io.reactivex.Single

class DataCacheDbImp(
    private val appDatabase: AppDatabase,
    private val characterDbMapper: CharacterDbMapper,
    private val locationDbMapper: LocationDbMapper,
    private val originDbMapper: OriginDbMapper,
    private val characterAllMapper: CharacterAllMapper
) : IDataCacheDb {
    override fun removeFavoriteCharacter(id: Int): Completable =
        appDatabase.characterDao().removeCharacter(id)

    override fun addFavoriteCharacter(character: Character): Completable =
        appDatabase.locationDao()
            .insert(locationDbMapper.map(character.location))
            .concatWith(
                appDatabase.originDao().insert(originDbMapper.map(character.origin))
                    .concatWith(appDatabase.characterDao().insert(characterDbMapper.map(character)))
            )


    override fun getAllFavoriteCharacters(): Single<List<Character>> =
        appDatabase.characterDao().getCharacters().map { characterAllMapper.map(it) }

    override fun isFavorite(id: Int): Single<Boolean> = appDatabase.characterDao().exist(id)
}
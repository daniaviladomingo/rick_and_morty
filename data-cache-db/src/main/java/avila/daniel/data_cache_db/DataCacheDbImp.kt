package avila.daniel.data_cache_db

import avila.daniel.data_cache.db.IDataCacheDb
import avila.daniel.data_cache_db.definition.AppDatabase
import avila.daniel.data_cache_db.model.mapper.CharacterMapperDb
import avila.daniel.domain.model.Character
import io.reactivex.Completable
import io.reactivex.Single

class DataCacheDbImp(
    private val appDatabase: AppDatabase,
    private val characterMapperDb: CharacterMapperDb
) : IDataCacheDb {
    override fun removeFavoriteCharacter(id: Int): Completable = Completable.create {
        appDatabase.characterDao().removeCharacter(id)
        it.onComplete()
    }

    override fun addFavoriteCharacter(character: Character): Completable = Completable.create {
        appDatabase.characterDao().insert(characterMapperDb.map(character))
        it.onComplete()
    }

    override fun getAllFavoriteCharacters(): Single<List<Character>> =
        appDatabase.characterDao().getCharacters().map { characterMapperDb.inverseMap(it) }

    override fun isFavorite(id: Int): Single<Boolean> =
        appDatabase.characterDao().exist(id).map { it != 0 }
}
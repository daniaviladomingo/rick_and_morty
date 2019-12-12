package avila.daniel.data_cache.db

import avila.daniel.domain.model.Character
import io.reactivex.Completable
import io.reactivex.Single

interface IDataCacheDb {
    fun removeFavoriteCharacter(id: Int): Completable
    fun addFavoriteCharacter(character: Character): Completable
    fun getAllFavoriteCharacters(): Single<List<Character>>
}
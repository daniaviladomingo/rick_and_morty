package avila.daniel.data_cache_db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import avila.daniel.data_cache_db.model.CharacterDb
import avila.daniel.data_cache_db.model.CharacterComplete
import io.reactivex.Completable
import io.reactivex.Single

@Dao
abstract class CharacterDao : BaseDao<CharacterDb> {
    @Transaction
    @Query("SELECT * FROM character")
    abstract fun getCharacters(): Single<List<CharacterComplete>>

    @Query("SELECT * FROM character WHERE id = :id")
    abstract fun getCharacter(id: Int): Single<CharacterDb>

    @Query("SELECT EXISTS(SELECT 1 FROM character WHERE id = :id LIMIT 1)")
    abstract fun exist(id: Int): Single<Boolean>

    @Query("DELETE FROM character WHERE id = :id")
    abstract fun removeCharacter(id: Int): Completable
}
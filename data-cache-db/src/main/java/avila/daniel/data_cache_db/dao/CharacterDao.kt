package avila.daniel.data_cache_db.dao

import androidx.room.Dao
import androidx.room.Query
import avila.daniel.data_cache_db.model.CharacterDb
import io.reactivex.Single

@Dao
abstract class CharacterDao : BaseDao<CharacterDb> {
    @Query("SELECT * FROM character")
    abstract fun getCharacters(): Single<List<CharacterDb>>

    @Query("SELECT * FROM character WHERE id = :id")
    abstract fun getCharacter(id: Int): Single<CharacterDb>

    @Query("SELECT COUNT(id) FROM character WHERE id = :id")
    abstract fun exist(id: Int): Single<Int>

    @Query("DELETE FROM character WHERE id = :id")
    abstract fun removeCharacter(id: Int)
}
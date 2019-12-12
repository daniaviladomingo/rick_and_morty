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
}
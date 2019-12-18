package avila.daniel.data_cache_db.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Completable

interface BaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj: T): Completable

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(obj: T): Completable

    @Delete
    fun delete(obj: T): Completable
}
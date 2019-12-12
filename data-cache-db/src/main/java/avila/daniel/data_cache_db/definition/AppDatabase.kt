package avila.daniel.data_cache_db.definition

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import avila.daniel.data_cache_db.dao.CharacterDao
import avila.daniel.data_cache_db.definition.typeconverter.GenderTypeConverter
import avila.daniel.data_cache_db.definition.typeconverter.StatusTypeConverter

private const val DATABASE_VERSION = 1

@Database(
    entities = [
        CharacterDao::class
    ],
    version = DATABASE_VERSION,
    exportSchema = false
)

@TypeConverters(
    StatusTypeConverter::class,
    GenderTypeConverter::class
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
}
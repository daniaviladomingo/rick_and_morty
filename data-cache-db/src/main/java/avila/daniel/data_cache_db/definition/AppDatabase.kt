package avila.daniel.data_cache_db.definition

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import avila.daniel.data_cache_db.dao.CharacterDao
import avila.daniel.data_cache_db.dao.LocationDao
import avila.daniel.data_cache_db.dao.OriginDao
import avila.daniel.data_cache_db.definition.typeconverter.GenderTypeConverter
import avila.daniel.data_cache_db.definition.typeconverter.StatusTypeConverter
import avila.daniel.data_cache_db.model.CharacterDb
import avila.daniel.data_cache_db.model.LocationDb
import avila.daniel.data_cache_db.model.OriginDb

private const val DATABASE_VERSION = 1

@Database(
    entities = [
        CharacterDb::class,
        LocationDb::class,
        OriginDb::class
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
    abstract fun locationDao(): LocationDao
    abstract fun originDao(): OriginDao
}
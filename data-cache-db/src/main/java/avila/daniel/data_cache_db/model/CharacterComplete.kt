package avila.daniel.data_cache_db.model

import androidx.room.Embedded
import androidx.room.Relation
import avila.daniel.data_cache_db.model.CharacterDb.Companion.COLUMN_NAME_ID_LOCATION
import avila.daniel.data_cache_db.model.CharacterDb.Companion.COLUMN_NAME_ID_ORIGIN

data class CharacterComplete(
    @Embedded val character: CharacterDb,
    @Relation(
        parentColumn = COLUMN_NAME_ID_LOCATION,
        entityColumn = LocationDb.COLUMN_NAME_NAME
    ) val location: LocationDb,
    @Relation(
        parentColumn = COLUMN_NAME_ID_ORIGIN,
        entityColumn = OriginDb.COLUMN_NAME_NAME
    ) val origin: OriginDb
)
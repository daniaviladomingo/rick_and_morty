package avila.daniel.data_cache_db.definition.typeconverter

import androidx.room.TypeConverter
import avila.daniel.domain.model.compose.Status

class StatusTypeConverter {

    @TypeConverter
    fun toStatus(value: String): Status = Status.valueOf(value)

    @TypeConverter
    fun toString(value: Status): String = value.toString()
}
package avila.daniel.data_cache_db.definition.typeconverter

import androidx.room.TypeConverter
import avila.daniel.domain.model.compose.Gender

class GenderTypeConverter {

    @TypeConverter
    fun toGender(value: String): Gender = Gender.valueOf(value)

    @TypeConverter
    fun toString(value: Gender): String = value.toString()
}
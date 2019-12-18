package avila.daniel.data_cache_db.model

import androidx.room.*
import avila.daniel.data_cache_db.model.CharacterDb.Companion.COLUMN_NAME_ID
import avila.daniel.data_cache_db.model.CharacterDb.Companion.COLUMN_NAME_ID_LOCATION
import avila.daniel.data_cache_db.model.CharacterDb.Companion.COLUMN_NAME_ID_ORIGIN
import avila.daniel.domain.model.compose.Gender
import avila.daniel.domain.model.compose.Status

@Entity(
    tableName = "character",
    indices = [Index(value = [COLUMN_NAME_ID])],
    foreignKeys = [ForeignKey(
        entity = OriginDb::class,
        childColumns = [COLUMN_NAME_ID_ORIGIN],
        parentColumns = [OriginDb.COLUMN_NAME_NAME]
    ),ForeignKey(
        entity = LocationDb::class,
        childColumns = [COLUMN_NAME_ID_LOCATION],
        parentColumns = [LocationDb.COLUMN_NAME_NAME]
    )]
)

data class CharacterDb(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_NAME_ID)
    val id: Int,

    @ColumnInfo(name = COLUMN_NAME_ID_LOCATION)
    val idLocation: String,

    @ColumnInfo(name = COLUMN_NAME_ID_ORIGIN)
    val idOrigin: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "status")
    val status: Status,

    @ColumnInfo(name = "species")
    val species: String,

    @ColumnInfo(name = "type")
    val type: String,

    @ColumnInfo(name = "gender")
    val gender: Gender,

    @ColumnInfo(name = "image")
    val image: String
){
    companion object{
        const val COLUMN_NAME_ID_LOCATION = "id_location"
        const val COLUMN_NAME_ID_ORIGIN = "id_origin"
        const val COLUMN_NAME_ID = "id"
    }
}
package avila.daniel.data_cache_db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import avila.daniel.domain.model.compose.Gender
import avila.daniel.domain.model.compose.Status

@Entity(
    tableName = "character",
    indices = [Index(value = ["id"], unique = true)]
)

data class CharacterDb(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int,

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
)
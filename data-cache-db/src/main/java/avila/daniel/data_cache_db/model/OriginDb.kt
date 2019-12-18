package avila.daniel.data_cache_db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "origin"
)

data class OriginDb(
    @PrimaryKey
    @ColumnInfo(name = COLUMN_NAME_NAME)
    val name: String,

    @ColumnInfo(name = "url")
    val url: String
) {
    companion object {
        const val COLUMN_NAME_NAME = "name"

    }
}
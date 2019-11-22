package avila.daniel.domain.model

import avila.daniel.domain.model.compose.Gender
import avila.daniel.domain.model.compose.LocationCompose
import avila.daniel.domain.model.compose.Origin
import avila.daniel.domain.model.compose.Status

data class Character(
    val created: String,
    val episode: List<String>,
    val gender: Gender,
    val id: Int,
    val image: String,
    val location: LocationCompose,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: Status,
    val type: String,
    val url: String
)
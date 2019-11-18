package avila.daniel.domain.model

import avila.daniel.domain.model.compose.LocationCompose
import avila.daniel.domain.model.compose.Origin

data class Character(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val locationCompose: LocationCompose,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)
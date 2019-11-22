package avila.daniel.repository.remote.model

import avila.daniel.domain.model.compose.LocationCompose
import avila.daniel.domain.model.compose.Origin
import avila.daniel.domain.model.compose.Status

data class CharacterApi(
    val created: String,
    val episode: List<String>,
    val gender: String,
    val id: Int,
    val image: String,
    val location: LocationCompose,
    val name: String,
    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)
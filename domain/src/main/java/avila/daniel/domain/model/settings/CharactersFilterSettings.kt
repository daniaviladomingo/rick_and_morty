package avila.daniel.domain.model.settings

import avila.daniel.domain.model.compose.Gender
import avila.daniel.domain.model.compose.Status

data class CharactersFilterSettings(
    val name: String,
    val status: Status,
    val specie: String,
    val type: String,
    val gender: Gender
)
package avila.daniel.domain.model

import avila.daniel.domain.model.settings.compose.CharacterFilterGenderParameter
import avila.daniel.domain.model.settings.compose.CharacterFilterStatusParameter

data class ParameterCharacter(
    var page: Int,
    var name: String,
    var status: CharacterFilterStatusParameter,
    var species: String,
    var type: String,
    var gender: CharacterFilterGenderParameter
)
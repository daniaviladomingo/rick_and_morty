package avila.daniel.domain.model.settings

import avila.daniel.domain.model.settings.compose.CharacterFilterGenderParameter
import avila.daniel.domain.model.settings.compose.CharacterFilterParameter
import avila.daniel.domain.model.settings.compose.CharacterFilterStatusParameter

data class CharactersFilterSettings(
    val parameterFilter: CharacterFilterParameter,
    val status: CharacterFilterStatusParameter,
    val gender: CharacterFilterGenderParameter
)
package avila.daniel.repository.cache.model

import avila.daniel.repository.cache.model.compose.CharacterFilterGenderParameter
import avila.daniel.repository.cache.model.compose.CharacterFilter
import avila.daniel.repository.cache.model.compose.CharacterFilterStatusParameter

data class CharactersFilterSettings(
    val parameterFilter: CharacterFilter,
    val status: CharacterFilterStatusParameter,
    val gender: CharacterFilterGenderParameter
)
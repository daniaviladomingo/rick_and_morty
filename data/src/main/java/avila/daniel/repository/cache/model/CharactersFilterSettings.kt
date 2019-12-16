package avila.daniel.repository.cache.model

import avila.daniel.repository.cache.model.compose.CharacterFilterGenderParameter
import avila.daniel.domain.model.filter.CharacterFilterParameter
import avila.daniel.repository.cache.model.compose.CharacterFilterStatusParameter

data class CharactersFilterSettings(
    val parameterFilter: CharacterFilterParameter,
    val status: CharacterFilterStatusParameter,
    val gender: CharacterFilterGenderParameter
)
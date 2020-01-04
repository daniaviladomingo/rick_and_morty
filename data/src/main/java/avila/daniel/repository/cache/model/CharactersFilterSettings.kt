package avila.daniel.repository.cache.model

import avila.daniel.repository.cache.model.compose.CharacterGenderFilter
import avila.daniel.repository.cache.model.compose.CharacterSearchFilter
import avila.daniel.repository.cache.model.compose.CharacterStatusFilter

data class CharactersFilterSettings(
    val search: CharacterSearchFilter,
    val status: CharacterStatusFilter,
    val gender: CharacterGenderFilter
)
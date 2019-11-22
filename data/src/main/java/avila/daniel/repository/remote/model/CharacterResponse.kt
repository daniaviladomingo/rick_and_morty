package avila.daniel.repository.remote.model

import avila.daniel.repository.remote.model.compose.Info

data class CharacterResponse(
    val info: Info,
    val results: List<CharacterApi>
)
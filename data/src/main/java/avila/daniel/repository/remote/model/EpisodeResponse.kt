package avila.daniel.repository.remote.model

import avila.daniel.repository.remote.model.compose.Info

data class EpisodeResponse(
    val info: Info,
    val results: List<EpisodeApi>
)
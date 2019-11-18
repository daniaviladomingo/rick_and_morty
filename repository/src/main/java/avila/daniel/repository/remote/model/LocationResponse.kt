package avila.daniel.repository.remote.model

import avila.daniel.domain.model.Location
import avila.daniel.repository.remote.model.compose.Info

data class LocationResponse(
    val info: Info,
    val results: List<Location>
)
package avila.daniel.rickmorty.ui.model

data class EpisodeUI(
    val id: Int,
    val name: String,
    val number: Int,
    val season: Int,
    val airDate: String,
    val characters: Int
)
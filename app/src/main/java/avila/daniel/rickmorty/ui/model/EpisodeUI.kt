package avila.daniel.rickmorty.ui.model

import avila.daniel.rickmorty.ui.util.DynamicSearchAdapter

data class EpisodeUI(
    val id: Int,
    val name: String,
    val number: Int,
    val season: Int,
    val airDate: String,
    val characters: Int
) : DynamicSearchAdapter.Searchable {
    override fun getSearchCriteria(): String = name
}
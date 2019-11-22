package avila.daniel.rickmorty.ui.model

import avila.daniel.rickmorty.ui.util.DynamicSearchAdapter

data class LocationUI(
    val name: String,
    val type: String,
    val dimension: String,
    val population: Int
) : DynamicSearchAdapter.Searchable {
    override fun getSearchCriteria(): String = "$name$dimension$type"
}
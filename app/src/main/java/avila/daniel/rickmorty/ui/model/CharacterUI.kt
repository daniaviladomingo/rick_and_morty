package avila.daniel.rickmorty.ui.model

import avila.daniel.rickmorty.ui.util.DynamicSearchAdapter

data class CharacterUI(
    val name: String,
    val photo: String,
    val status: String
) : DynamicSearchAdapter.Searchable {
    override fun getSearchCriteria(): String = "$name"
}
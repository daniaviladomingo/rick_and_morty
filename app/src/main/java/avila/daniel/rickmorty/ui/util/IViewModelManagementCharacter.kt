package avila.daniel.rickmorty.ui.util

import avila.daniel.domain.model.Character
import avila.daniel.repository.cache.model.compose.CharacterSearchFilter

interface IViewModelManagementCharacter {
    fun openDetail(character: Character)
    fun searchFilter(): CharacterSearchFilter
}
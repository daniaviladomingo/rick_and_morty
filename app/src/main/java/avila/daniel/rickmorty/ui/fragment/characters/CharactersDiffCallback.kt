package avila.daniel.rickmorty.ui.fragment.characters

import androidx.recyclerview.widget.DiffUtil
import avila.daniel.domain.model.Character
import avila.daniel.repository.cache.model.compose.CharacterFilterParameter

class CharactersDiffCallback : DiffUtil.Callback() {
    lateinit var listOld: List<Character>
    lateinit var listNew: List<Character>

    var currentFilter: CharacterFilterParameter? = null
    var newFilter: CharacterFilterParameter? = null

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        listOld[oldItemPosition].id == listNew[newItemPosition].id

    override fun getOldListSize(): Int = listOld.size

    override fun getNewListSize(): Int = listNew.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        currentFilter == newFilter
}
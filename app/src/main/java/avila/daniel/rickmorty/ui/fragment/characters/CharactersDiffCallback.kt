package avila.daniel.rickmorty.ui.fragment.characters

import androidx.recyclerview.widget.DiffUtil
import avila.daniel.rickmorty.ui.model.CharacterUI
import avila.daniel.rickmorty.ui.model.EpisodeUI

class CharactersDiffCallback(
    private val listOld: List<CharacterUI>,
    private val listNew: List<CharacterUI>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        listOld[oldItemPosition].id == listNew[newItemPosition].id

    override fun getOldListSize(): Int = listOld.size

    override fun getNewListSize(): Int = listNew.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        listOld[oldItemPosition].name == listNew[newItemPosition].name
}
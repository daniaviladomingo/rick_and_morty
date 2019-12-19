package avila.daniel.rickmorty.ui.fragment.episodes

import androidx.recyclerview.widget.DiffUtil
import avila.daniel.domain.model.Character
import avila.daniel.rickmorty.ui.model.EpisodeUI

class EpisodesDiffCallback : DiffUtil.Callback() {
    lateinit var listOld: List<Any>
    lateinit var listNew: List<Any>

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = listOld[oldItemPosition]
        val newItem = listNew[newItemPosition]
        return if (oldItem is EpisodeUI && newItem is EpisodeUI) {
            oldItem.id == newItem.id
        } else if (oldItem is ItemHeader && newItem is ItemHeader) {
            oldItem.title == newItem.title
        } else {
            false
        }
    }

    override fun getOldListSize(): Int = listOld.size

    override fun getNewListSize(): Int = listNew.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = listOld[oldItemPosition]
        val newItem = listNew[newItemPosition]

        return if (oldItem is EpisodeUI && newItem is EpisodeUI) {
            oldItem.name == newItem.name
        } else if (oldItem is ItemHeader && newItem is ItemHeader) {
            oldItem.title == newItem.title
        } else {
            false
        }
    }
}
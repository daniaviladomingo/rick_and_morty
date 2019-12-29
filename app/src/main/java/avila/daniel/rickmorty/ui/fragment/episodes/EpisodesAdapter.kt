package avila.daniel.rickmorty.ui.fragment.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.ui.model.EpisodeUI
import com.yuyang.stickyheaders.AdapterDataProvider
import com.yuyang.stickyheaders.StickyHeaderModel
import kotlinx.android.synthetic.main.header_episode.view.*
import kotlinx.android.synthetic.main.item_episode.view.*

class EpisodesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), AdapterDataProvider {

    private val data = mutableListOf<Any>()
    var onClickListener: ((Int, String) -> Unit)? = null
    private val diffCallback = object : DiffUtil.Callback() {
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

    fun setData(newData: List<EpisodeUI>) {
        diffCallback.listOld = data
        val newDataWithHeader = managementHeaders(newData)
        diffCallback.listNew = newDataWithHeader
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        data.clear()
        data.addAll(newDataWithHeader)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun managementHeaders(episodes: List<EpisodeUI>): List<Any> {
        val newList = mutableListOf<Any>()

        if (data.size == 0) {
            newList.add(ItemHeader("${episodes[0].season}"))
        } else {
            val lastItem = data[data.size - 1] as EpisodeUI
            val firstItem = episodes[0]
            if (lastItem.season != firstItem.season) {
                newList.add(ItemHeader("${firstItem.season}"))
            }
        }

        episodes.forEachIndexed { index, _ ->
            if (index > 0) {
                val item0 = episodes[index - 1]
                val item1 = episodes[index]
                if (item0.season != item1.season) {
                    newList.add(ItemHeader("${item1.season}"))
                }
                newList.add(item1)
            } else {
                newList.add(episodes[0])
            }
        }

        return newList
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == 0) {
            UserViewHolder.create(parent)
        } else {
            HeaderViewHolder.create(parent)
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        if (item is EpisodeUI) {
            (holder as UserViewHolder).bin(data[position] as EpisodeUI, onClickListener)
        } else if (item is StickyHeaderModel) {
            (holder as HeaderViewHolder).bin(data[position] as ItemHeader)
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (data[position] is EpisodeUI) {
            0
        } else {
            1
        }

    override fun getAdapterData(): List<Any> = data
}

private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bin(episode: EpisodeUI, onClickListener: ((Int, String) -> Unit)?) {
        itemView.run {
            name.text = episode.name
            this.episode.text = "${episode.number}"
            on_air.text = episode.airDate
            characters.text = "${episode.characters}"
            setOnClickListener {
                onClickListener?.invoke(episode.id, episode.name)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder =
            UserViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_episode,
                    parent,
                    false
                )
            )
    }
}

private class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bin(header: ItemHeader) {
        itemView.run {
            title_header.text = String.format("Season %s", header.title)
        }
    }

    companion object {
        fun create(parent: ViewGroup): HeaderViewHolder =
            HeaderViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.header_episode,
                    parent,
                    false
                )
            )
    }
}
package avila.daniel.rickmorty.ui.fragment.episodes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.databinding.ItemEpisodeBinding
import avila.daniel.rickmorty.databinding.ItemHeaderEpisodeBinding
import avila.daniel.rickmorty.ui.model.EpisodeUI
import avila.daniel.rickmorty.ui.util.IDataSet
import com.yuyang.stickyheaders.AdapterDataProvider
import com.yuyang.stickyheaders.StickyHeaderModel

class EpisodesAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), AdapterDataProvider,
    IDataSet<EpisodeUI> {

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

    override fun setData(newData: List<EpisodeUI>) {
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

        if (episodes.isNotEmpty()) {
            if (data.isEmpty()) {
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
        }

        return newList
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == 0) {
            EpisodeViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_episode, parent, false
                ), onClickListener
            )
        } else {
            HeaderViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_header_episode, parent, false
                )
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = data[position]
        if (item is EpisodeUI) {
            (holder as EpisodeViewHolder).bin(data[position] as EpisodeUI)
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

    data class ItemHeader(val title: String) : StickyHeaderModel

    class EpisodeViewHolder(
        private val binding: ItemEpisodeBinding,
        onClickListener: ((Int, String) -> Unit)?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.episode?.let {
                    onClickListener?.invoke(it.id, it.name)
                }
            }
        }

        fun bin(episode: EpisodeUI) {
            with(binding) {
                this.episode = episode
            }
        }
    }

    class HeaderViewHolder(private val binding: ItemHeaderEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bin(header: ItemHeader) {
            with(binding) {
                title = String.format("Season %s", header.title)
            }
        }
    }
}
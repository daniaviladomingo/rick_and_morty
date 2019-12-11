package avila.daniel.rickmorty.ui.fragment.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.ui.model.EpisodeUI
import com.yuyang.stickyheaders.AdapterDataProvider
import com.yuyang.stickyheaders.StickyHeaderModel
import kotlinx.android.synthetic.main.header_episode.view.*
import kotlinx.android.synthetic.main.item_episode.view.*

class EpisodesAdapter(
    private val userList: List<Any>,
    private val onClickListener: (Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), AdapterDataProvider {
    override fun getItemCount(): Int = userList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == 0) {
            UserViewHolder.create(parent)
        } else {
            HeaderViewHolder.create(parent)
        }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = userList[position]
        if (item is EpisodeUI) {
            (holder as UserViewHolder).bin(userList[position] as EpisodeUI, onClickListener)
        } else if (item is StickyHeaderModel) {
            (holder as HeaderViewHolder).bin(userList[position] as ItemHeader)
        }

    }

    override fun getItemViewType(position: Int): Int =
        if (userList[position] is EpisodeUI) {
            0
        } else {
            1
        }

    override fun getAdapterData(): List<Any> = userList
}

private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bin(episode: EpisodeUI, onClickListener: (Int) -> Unit) {
        itemView.run {
            name.text = episode.name
            season.text =
                "${episode.season}"
            this.episode.text = "${episode.number}"
            on_air.text = episode.airDate
            characters.text = "${episode.characters}"
            setOnClickListener {
                onClickListener(episode.id)
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
            title_header.text = "Season ${header.title}"
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
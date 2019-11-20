package avila.daniel.rickmorty.ui.episodes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.ui.model.EpisodeUI
import kotlinx.android.synthetic.main.item_episode.view.*

class EpisodesAdapter(private val userList: List<EpisodeUI>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = userList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        UserViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as UserViewHolder).bin(userList[position])
}

private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bin(episode: EpisodeUI) {
        itemView.run {
            name.text = episode.name
            season.text =
                "${episode.season}"
            this.episode.text = "${episode.number}"
            on_air.text = episode.airDate
            characters.text = "${episode.characters}"
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
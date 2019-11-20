package avila.daniel.rickmorty.ui.characters

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.ui.model.CharacterUI
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_charter.view.*

class CharactersAdapter(private val userList: List<CharacterUI>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = userList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        UserViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as UserViewHolder).bin(userList[position])
}

private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bin(character: CharacterUI) {
        itemView.run {
            name.text = character.name
            specie.text = character.specie
            Glide.with(itemView).load(character.photo).into(photo)
        }
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder = UserViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_charter,
                parent,
                false
            )
        )
    }
}
package avila.daniel.rickmorty.ui.fragment.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.ui.model.CharacterUI
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_charter.view.*

class CharactersAdapter(
    private val diffCallback: CharactersDiffCallback,
    private val onClickListener: (Int,String) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val characterList = mutableListOf<CharacterUI>()

    fun update(newCharacters: List<CharacterUI>) {
        diffCallback.listOld = characterList
        diffCallback.listNew = newCharacters
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        characterList.clear()
        characterList.addAll(newCharacters)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = characterList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        UserViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as UserViewHolder).bin(characterList[position], onClickListener)
}

private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bin(character: CharacterUI, onClickListener: (Int, String) -> Unit) {
        itemView.run {
            name.text = character.name
            Glide.with(itemView).load(character.photo).into(photo)
            setOnClickListener {
                onClickListener(character.id, character.name)
            }
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
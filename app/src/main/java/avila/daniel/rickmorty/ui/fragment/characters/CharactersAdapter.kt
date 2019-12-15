package avila.daniel.rickmorty.ui.fragment.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.domain.model.Character
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_charter.view.*

class CharactersAdapter(
    private val diffCallback: CharactersDiffCallback,
    private val onClickListener: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val characterList = mutableListOf<Character>()

    fun update(newCharacters: List<Character>) {
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
    fun bin(character: Character, onClickListener: (Int) -> Unit) {
        itemView.run {
            name.text = character.name.run { if (length > 20) substring(0..20) + "..." else this }
            Glide.with(itemView).load(character.image).into(photo)
            setOnClickListener {
                onClickListener(character.id)
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
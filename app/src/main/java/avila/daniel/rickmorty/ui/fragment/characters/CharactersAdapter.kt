package avila.daniel.rickmorty.ui.fragment.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.domain.model.Character
import avila.daniel.repository.cache.model.compose.CharacterFilterParameter
import avila.daniel.rickmorty.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_charter.view.*

class CharactersAdapter(
    private val diffCallback: CharactersDiffCallback,
    private val searchFilter: () -> CharacterFilterParameter
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val characterList = mutableListOf<Character>()

    var onClickListener: ((Int) -> Unit)? = null

    fun update(newCharacters: List<Character>) {
        diffCallback.listOld = characterList
        diffCallback.listNew = newCharacters
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        characterList.clear()
        characterList.addAll(newCharacters)
        diffResult.dispatchUpdatesTo(this)
    }

    fun refresh() {
        diffCallback.newFilter = searchFilter()
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        diffCallback.currentFilter = diffCallback.newFilter
    }

    override fun getItemCount(): Int = characterList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        UserViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as UserViewHolder).bin(characterList[position], onClickListener, searchFilter)
}

private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bin(
        character: Character,
        onClickListener: ((Int) -> Unit)?,
        searchFilter: () -> CharacterFilterParameter
    ) {
        itemView.run {
            name.text =
                when (searchFilter()) {
                    CharacterFilterParameter.NAME -> character.name
                    CharacterFilterParameter.SPECIES -> character.species
                    CharacterFilterParameter.TYPE -> character.type
                }.run { if (length > 20) substring(0..20) + "..." else if (this.isEmpty()) "???" else this }

            Glide.with(itemView).load(character.image).into(photo)
            setOnClickListener {
                onClickListener?.invoke(character.id)
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
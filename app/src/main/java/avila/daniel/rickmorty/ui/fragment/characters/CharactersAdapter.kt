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
    private val searchFilter: () -> CharacterFilterParameter
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val data = mutableListOf<Character>()

    private val diffCallback = object : DiffUtil.Callback() {
        lateinit var listOld: List<Character>
        lateinit var listNew: List<Character>

        var currentFilter: CharacterFilterParameter? = null
        var newFilter: CharacterFilterParameter? = null

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            listOld[oldItemPosition].id == listNew[newItemPosition].id

        override fun getOldListSize(): Int = listOld.size

        override fun getNewListSize(): Int = listNew.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            currentFilter == newFilter
    }

    var onClickListener: ((Character) -> Unit)? = null

    fun setData(newData: List<Character>) {
        diffCallback.listOld = data
        diffCallback.listNew = newData
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun refresh() {
        diffCallback.newFilter = searchFilter()
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        diffCallback.currentFilter = diffCallback.newFilter
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        UserViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as UserViewHolder).bin(data[position], onClickListener, searchFilter)


}

private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bin(
        character: Character,
        onClickListener: ((Character) -> Unit)?,
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
                onClickListener?.invoke(character)
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
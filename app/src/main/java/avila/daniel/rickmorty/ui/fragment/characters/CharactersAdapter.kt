package avila.daniel.rickmorty.ui.fragment.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.domain.model.Character
import avila.daniel.repository.cache.model.compose.CharacterSearchFilter
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.databinding.ItemCharterBinding

class CharactersAdapter(
    private val characterSearchFilter: () -> CharacterSearchFilter
) : RecyclerView.Adapter<CharactersAdapter.ViewHolder>() {

    private val data = mutableListOf<Character>()
    private val diffCallback = object : DiffUtil.Callback() {
        lateinit var listOld: List<Character>
        lateinit var listNew: List<Character>

        var currentFilter: CharacterSearchFilter? = null
        var newFilter: CharacterSearchFilter? = null

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
        diffCallback.newFilter = characterSearchFilter()
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        diffCallback.currentFilter = diffCallback.newFilter
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_charter, parent, false
        ), onClickListener
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bin(data[position], characterSearchFilter)

    class ViewHolder(
        private val binding: ItemCharterBinding,
        onClickListener: ((Character) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.character?.let {
                    onClickListener?.invoke(it)
                }

            }
        }

        fun bin(
            character: Character,
            characterSearchFilter: () -> CharacterSearchFilter
        ) {
            with(binding) {
                this.character = character
                this.characterFilter = characterSearchFilter
            }
        }
    }
}


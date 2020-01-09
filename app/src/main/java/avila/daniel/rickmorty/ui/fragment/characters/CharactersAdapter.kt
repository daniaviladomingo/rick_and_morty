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
import avila.daniel.rickmorty.ui.util.IDataSet
import avila.daniel.rickmorty.ui.util.IViewModelManagementCharacter

class CharactersAdapter(
    private val viewModel: IViewModelManagementCharacter
) : RecyclerView.Adapter<CharactersAdapter.ViewHolder>(), IDataSet<Character> {

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

    override fun setData(newData: List<Character>) {
        diffCallback.listOld = data
        diffCallback.listNew = newData
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun refresh() {
        diffCallback.newFilter = viewModel.searchFilter()
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
        diffCallback.currentFilter = diffCallback.newFilter
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_charter, parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bin(viewModel, data[position])

    class ViewHolder(private val binding: ItemCharterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bin(viewModel: IViewModelManagementCharacter, character: Character) {
            with(binding) {
                this.viewModel = viewModel
                this.character = character
                executePendingBindings()
            }
        }
    }
}


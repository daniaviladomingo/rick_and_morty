package avila.daniel.rickmorty.ui.fragment.locations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.databinding.ItemLocationBinding
import avila.daniel.rickmorty.ui.model.LocationUI
import avila.daniel.rickmorty.ui.util.IDataSet

class LocationsAdapter(
    private val viewModel: LocationsViewModel
) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>(), IDataSet<LocationUI> {
    private val data = mutableListOf<LocationUI>()
    var onClickListener: ((Int, String) -> Unit)? = null
    private val diffCallback = object : DiffUtil.Callback() {
        lateinit var listOld: List<LocationUI>
        lateinit var listNew: List<LocationUI>

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            listOld[oldItemPosition].id == listNew[newItemPosition].id

        override fun getOldListSize(): Int = listOld.size

        override fun getNewListSize(): Int = listNew.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            listOld[oldItemPosition].name == listNew[newItemPosition].name
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_location, parent, false
        ),
        onClickListener
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun setData(newData: List<LocationUI>) {
        diffCallback.listOld = data
        diffCallback.listNew = newData

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(
        private val binding: ItemLocationBinding,
        var onClickListener: ((Int, String) -> Unit)?
    ) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setClickListener {
                binding.location?.let {
                    onClickListener?.invoke(it.id, it.name)
                }
            }
        }

        fun bind(location: LocationUI) {
            with(binding) {
                this.location = location
                executePendingBindings()
            }
        }
    }
}
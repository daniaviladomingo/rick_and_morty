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
        )
    )

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(viewModel, data[position])

    override fun setData(newData: List<LocationUI>) {
        diffCallback.listOld = data
        diffCallback.listNew = newData

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        data.clear()
        data.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(private val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(viewModel: LocationsViewModel, location: LocationUI) {
            with(binding) {
                this.viewModel = viewModel
                this.location = location
                executePendingBindings()
            }
        }
    }
}
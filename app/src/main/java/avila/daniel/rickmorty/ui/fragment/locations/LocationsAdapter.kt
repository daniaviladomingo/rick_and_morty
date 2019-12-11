package avila.daniel.rickmorty.ui.fragment.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.ui.model.LocationUI
import kotlinx.android.synthetic.main.item_location.view.*

class LocationsAdapter(private val onClickListener: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val locationList = mutableListOf<LocationUI>()

    fun update(newCharacters: List<LocationUI>) {
        val diffCallback = LocationDiffCallback(locationList, newCharacters)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        locationList.clear()
        locationList.addAll(newCharacters)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int = locationList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        UserViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as UserViewHolder).bin(locationList[position], onClickListener)
}

private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bin(location: LocationUI, onClickListener: (Int) -> Unit) {
        itemView.run {
            name.text = location.name
            type.text = location.type
            dimension.text = location.dimension
            population.text = "${location.population}"
            setOnClickListener {
                onClickListener(location.id)
            }
        }
    }

    companion object {
        fun create(parent: ViewGroup): UserViewHolder =
            UserViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_location,
                    parent,
                    false
                )
            )
    }
}
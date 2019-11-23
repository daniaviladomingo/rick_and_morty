package avila.daniel.rickmorty.ui.fragment.locations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.ui.model.LocationUI
import kotlinx.android.synthetic.main.item_location.view.*

class LocationsAdapter(private val userList: List<LocationUI>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int = userList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        UserViewHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) =
        (holder as UserViewHolder).bin(userList[position])
}

private class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bin(location: LocationUI) {
        itemView.run {
            name.text = location.name
            type.text = location.type
            dimension.text = location.dimension
            population.text = "${location.population}"
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
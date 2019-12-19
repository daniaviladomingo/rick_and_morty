package avila.daniel.rickmorty.ui.fragment.locations

import androidx.recyclerview.widget.DiffUtil
import avila.daniel.repository.cache.model.LocationFilterParameter
import avila.daniel.rickmorty.ui.model.LocationUI

class LocationsDiffCallback: DiffUtil.Callback() {
    lateinit var listOld: List<LocationUI>
    lateinit var listNew: List<LocationUI>

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        listOld[oldItemPosition].id == listNew[newItemPosition].id

    override fun getOldListSize(): Int = listOld.size

    override fun getNewListSize(): Int = listNew.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        listOld[oldItemPosition].name == listNew[newItemPosition].name

}
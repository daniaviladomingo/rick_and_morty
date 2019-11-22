package avila.daniel.rickmorty.base

import avila.daniel.rickmorty.ui.util.DynamicSearchAdapter
import avila.daniel.rickmorty.ui.util.ISearchList

abstract class SearchableFragment : InitialLoadFragment(), ISearchList {

    override fun updateCriteria(value: String) {
        getSearchable().search(value)
    }

    abstract fun getSearchable(): DynamicSearchAdapter<*>
}
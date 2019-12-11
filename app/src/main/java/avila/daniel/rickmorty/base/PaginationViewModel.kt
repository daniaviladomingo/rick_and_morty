package avila.daniel.rickmorty.base

import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.util.SingleLiveEvent

abstract class PaginationViewModel<T, U>(
    private val scheduleProvider: IScheduleProvider,
    private val initialPage: Int
) : BaseViewModel() {

    private var isLoading = false
    private var currentPage = initialPage
    private var totalPage = initialPage
    private var currentSearchFilter = ""
    private var clear = false

    val itemsLiveData = SingleLiveEvent<Resource<Pair<Boolean, List<U>>>>()

    fun scrollEnd() {
        if (!isLoading) {
            isLoading = true
            load()
        }
    }

    fun updateSearchFilter(newSearchFilter: String) {
        if (newSearchFilter != currentSearchFilter) {
            currentSearchFilter = newSearchFilter
            clearNReload()
        }
    }

    protected fun clearNReload() {
        clear = true
        currentPage = initialPage
        totalPage = initialPage
        load()
    }

    fun load() {
        if (currentPage <= totalPage) {
            itemsLiveData.value = Resource.loading()
            dispose()
            addDisposable(query().execute(Pair(currentSearchFilter, currentPage))
                .observeOn(scheduleProvider.ui())
                .subscribeOn(scheduleProvider.io())
                .subscribe({ items ->
                    itemsLiveData.value = Resource.success(
                        Pair(
                            clear,
                            mapper()?.map(items.second) ?: items.second as List<U>
                        )
                    )
                    clear = false
                    totalPage = items.first
                    currentPage++
                    isLoading = false
                }) {
                    isLoading = false
                    itemsLiveData.value = Resource.error(it.localizedMessage)
                })
        }
    }

    protected abstract fun query(): SingleUseCaseWithParameter<Pair<String, Int>, Pair<Int, List<T>>>

    protected open fun mapper(): Mapper<T, U>? = null


}
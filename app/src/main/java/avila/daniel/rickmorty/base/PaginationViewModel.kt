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

    private val dataList = mutableListOf<U>()

    private var isLoading = false
    private var currentPage = initialPage
    private var totalPage = initialPage
    private var currentSearchText = ""

    val itemsLiveData = SingleLiveEvent<Resource<List<U>>>()

    fun scrollEnd() {
        if (!isLoading) {
            isLoading = true
            load()
        }
    }

    fun searchText(searchText: String) {
        if (searchText != currentSearchText) {
            currentSearchText = searchText
            clearNReload()
        }
    }

    protected fun clearNReload() {
        dataList.clear()
        currentPage = initialPage
        totalPage = initialPage
        load()
    }

    fun load() {
        if (currentPage <= totalPage) {
            itemsLiveData.value = Resource.loading()
            dispose()
            addDisposable(query().execute(Pair(currentSearchText, currentPage++))
                .observeOn(scheduleProvider.ui())
                .subscribeOn(scheduleProvider.io())
                .subscribe({ items ->
                    dataList.addAll(mapper()?.map(items.second) ?: items.second as List<U>)
                    itemsLiveData.value = Resource.success(dataList)
                    totalPage = items.first
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
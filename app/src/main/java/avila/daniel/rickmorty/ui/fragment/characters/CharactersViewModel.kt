package avila.daniel.rickmorty.ui.fragment.characters

import avila.daniel.domain.interactor.GetCharactersUseCase
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.CharacterUI
import avila.daniel.rickmorty.ui.model.mapper.CharacterUIMapper
import avila.daniel.rickmorty.ui.util.IReloadData
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val characterUIMapper: CharacterUIMapper,
    private val scheduleProvider: IScheduleProvider,
    private val initialPage: Int
) : BaseViewModel(), IReloadData {

    val charactersLiveData = SingleLiveEvent<Resource<Pair<Boolean, List<CharacterUI>>>>()

    private var isLoading = false
    private var currentPage = initialPage
    private var currentSearchFilter = ""
    private var clear = false

    fun listScrolled(
        visibleItemCount: Int,
        lastVisibleItemPosition: Int,
        totalItemCount: Int
    ) {
        if (!isLoading) {
            if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
                isLoading = true
                loadCharacteres()
            }
        }
    }

    fun loadCharacteres() {
        charactersLiveData.value = Resource.loading()
        dispose()
        addDisposable(getCharactersUseCase.execute(Pair(currentSearchFilter, currentPage))
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({ characters ->
                charactersLiveData.value =
                    Resource.success(Pair(clear, characterUIMapper.map(characters)))
                clear = false
                currentPage++
                isLoading = false
            }) {
                isLoading = false
                charactersLiveData.value = Resource.error(it.localizedMessage)
            })
    }

    fun updateSearchFilter(newSearchFilter: String) {
        if (newSearchFilter != currentSearchFilter) {
            currentSearchFilter = newSearchFilter
            clearNReload()
        }
    }

    private fun clearNReload() {
        clear = true
        currentPage = initialPage
        loadCharacteres()
    }

    override fun reload() {
        clearNReload()
    }
}
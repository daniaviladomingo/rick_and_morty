package avila.daniel.rickmorty.ui.fragment.characters

import avila.daniel.domain.interactor.GetCharactersUseCase
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.base.PaginationViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.CharacterUI
import avila.daniel.rickmorty.ui.model.mapper.CharacterUIMapper
import avila.daniel.rickmorty.ui.util.IReloadData

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val characterUIMapper: CharacterUIMapper,
    scheduleProvider: IScheduleProvider,
    initialPage: Int
) : PaginationViewModel<Character, CharacterUI>(scheduleProvider, initialPage), IReloadData {

    override fun reload() {
        clearNReload()
    }

    override fun query(): SingleUseCaseWithParameter<Pair<String, Int>, Pair<Int, List<Character>>> =
        getCharactersUseCase

    override fun mapper(): Mapper<Character, CharacterUI>? = characterUIMapper

//    val charactersLiveData = SingleLiveEvent<Resource<Pair<Boolean, List<CharacterUI>>>>()
//
//    private var isLoading = false
//    private var currentPage = initialPage
//    private var totalPage = initialPage
//    private var currentSearchFilter = ""
//    private var clear = false
//
//    fun listScrolled(
//        visibleItemCount: Int,
//        lastVisibleItemPosition: Int,
//        totalItemCount: Int
//    ) {
//        if (!isLoading) {
//            if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
//                isLoading = true
//                loadCharacteres()
//            }
//        }
//    }
//
//    fun loadCharacteres() {
//        if (currentPage <= totalPage) {
//            charactersLiveData.value = Resource.loading()
//            dispose()
//            addDisposable(getCharactersUseCase.execute(Pair(currentSearchFilter, currentPage))
//                .observeOn(scheduleProvider.ui())
//                .subscribeOn(scheduleProvider.io())
//                .subscribe({ characters ->
//                    charactersLiveData.value =
//                        Resource.success(Pair(clear, characterUIMapper.map(characters.second)))
//                    clear = false
//                    totalPage = characters.first
//                    currentPage++
//                    isLoading = false
//                }) {
//                    isLoading = false
//                    charactersLiveData.value = Resource.error(it.localizedMessage)
//                })
//        }
//    }
//
//    fun updateSearchFilter(newSearchFilter: String) {
//        if (newSearchFilter != currentSearchFilter) {
//            currentSearchFilter = newSearchFilter
//            clearNReload()
//        }
//    }
//
//    private fun clearNReload() {
//        clear = true
//        currentPage = initialPage
//        totalPage = initialPage
//        loadCharacteres()
//    }
}
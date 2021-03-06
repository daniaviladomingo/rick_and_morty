package avila.daniel.rickmorty.ui.fragment.characters

import avila.daniel.domain.interactor.GetCharactersUseCase
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Character
import avila.daniel.repository.cache.model.compose.CharacterSearchFilter
import avila.daniel.rickmorty.base.PaginationViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.CharacterParcelable
import avila.daniel.rickmorty.ui.model.mapper.CharacterParcelableMapper
import avila.daniel.rickmorty.ui.util.IDataChanged
import avila.daniel.rickmorty.ui.util.IViewModelManagementCharacter
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val characterParcelableMapper: CharacterParcelableMapper,
    private val searchFilter: () -> CharacterSearchFilter,
    scheduleProvider: IScheduleProvider,
    initialPage: Int
) : PaginationViewModel<Character, Character>(scheduleProvider, initialPage), IDataChanged,
    IViewModelManagementCharacter {

    val characterParcelableLiveData = SingleLiveEvent<CharacterParcelable>()
    val refreshLiveData = SingleLiveEvent<Void>()

    override fun reload() {
        clearNReload()
    }

    override fun refresh() {
        refreshLiveData.call()
    }

    override fun query(): SingleUseCaseWithParameter<Pair<String, Int>, Pair<Int, List<Character>>> =
        getCharactersUseCase

    override fun openDetail(character: Character) {
        characterParcelableLiveData.value = characterParcelableMapper.map(character)
    }

    override fun searchFilter(): CharacterSearchFilter = searchFilter.invoke()
}
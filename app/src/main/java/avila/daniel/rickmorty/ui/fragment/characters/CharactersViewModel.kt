package avila.daniel.rickmorty.ui.fragment.characters

import avila.daniel.domain.interactor.GetCharactersUseCase
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Character
import avila.daniel.rickmorty.base.PaginationViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.CharacterParcelable
import avila.daniel.rickmorty.ui.model.mapper.CharacterParcelableMapper
import avila.daniel.rickmorty.ui.util.IDataChanged
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val characterParcelableMapper: CharacterParcelableMapper,
    scheduleProvider: IScheduleProvider,
    initialPage: Int
) : PaginationViewModel<Character, Character>(scheduleProvider, initialPage), IDataChanged {

    val characterParcelabeLiveData = SingleLiveEvent<Resource<CharacterParcelable>>()
    val refreshLiveData = SingleLiveEvent<Void>()

    override fun reload() {
        clearNReload()
    }

    override fun refresh() {
        refreshLiveData.call()
    }

    override fun query(): SingleUseCaseWithParameter<Pair<String, Int>, Pair<Int, List<Character>>> =
        getCharactersUseCase

    fun openCharacterDetail(character: Character){
        characterParcelabeLiveData.value = Resource.success(characterParcelableMapper.map(character))
    }
}
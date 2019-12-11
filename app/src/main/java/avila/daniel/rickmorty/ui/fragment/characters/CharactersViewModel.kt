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
}
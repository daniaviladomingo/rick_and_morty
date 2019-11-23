package avila.daniel.rickmorty.ui

import avila.daniel.domain.interactor.GetCharactersUseCase
import avila.daniel.domain.interactor.GetEpisodeCharactersUseCase
import avila.daniel.domain.interactor.GetLocationCharactersUseCase
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.CharacterUI
import avila.daniel.rickmorty.ui.model.mapper.CharacterUIMapper
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharactersLocationViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getLocationCharactersUseCase: GetLocationCharactersUseCase,
    private val getEpisodeCharactersUseCase: GetEpisodeCharactersUseCase,
    private val characterUIMapper: CharacterUIMapper,
    private val scheduleProvider: IScheduleProvider
    ): BaseViewModel() {
    val charactersLiveData = SingleLiveEvent<Resource<List<CharacterUI>>>()

    fun loadCharacters(){

    }

}
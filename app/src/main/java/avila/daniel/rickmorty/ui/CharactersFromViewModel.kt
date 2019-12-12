package avila.daniel.rickmorty.ui

import avila.daniel.domain.interactor.GetEpisodeCharactersUseCase
import avila.daniel.domain.interactor.GetLocationCharactersUseCase
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.CharacterUI
import avila.daniel.rickmorty.ui.model.CharactersSource
import avila.daniel.rickmorty.ui.model.mapper.CharacterUIMapper
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharactersFromViewModel(
    private val getLocationCharactersUseCase: GetLocationCharactersUseCase,
    private val getEpisodeCharactersUseCase: GetEpisodeCharactersUseCase,
    private val characterUIMapper: CharacterUIMapper,
    private val scheduleProvider: IScheduleProvider
) : BaseViewModel() {

    val charactersLiveData = SingleLiveEvent<Resource<List<CharacterUI>>>()

    fun loadCharacters(id: Int, charactersSource: CharactersSource) {
        charactersLiveData.value = Resource.loading()
        addDisposable(
            when (charactersSource) {
                CharactersSource.EPISODE -> getEpisodeCharactersUseCase.execute(id)
                CharactersSource.LOCATION -> getLocationCharactersUseCase.execute(id)
            }
                .observeOn(scheduleProvider.ui())
                .subscribeOn(scheduleProvider.io())
                .subscribe({ characters ->
                    charactersLiveData.value = Resource.success(characterUIMapper.map(characters))

                }) {

                    charactersLiveData.value = Resource.error(it.localizedMessage)
                })
    }
}
package avila.daniel.rickmorty.ui.locations

import avila.daniel.domain.interactor.GetCharactersUseCase
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.data.Resource
import avila.daniel.rickmorty.ui.model.CharacterUI
import avila.daniel.rickmorty.ui.model.mapper.CharacterUIMapper
import avila.daniel.rickmorty.util.SingleLiveEvent

class LocationsViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val scheduleProvider: IScheduleProvider,
    private val characterUIMapper: CharacterUIMapper
) : BaseViewModel() {

    val charactersLiveData = SingleLiveEvent<Resource<List<CharacterUI>?>>()

    fun loadMoreCharacteres() {
        charactersLiveData.value = Resource.loading()
        addDisposable(getCharactersUseCase.execute()
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({ characters ->
                charactersLiveData.value =
                    Resource.success(characters?.run { characterUIMapper.map(this) })
            }) {
                charactersLiveData.value = Resource.error(it.localizedMessage)
            })
    }
}
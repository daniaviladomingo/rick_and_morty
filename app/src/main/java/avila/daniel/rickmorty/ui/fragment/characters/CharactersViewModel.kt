package avila.daniel.rickmorty.ui.fragment.characters

import avila.daniel.domain.interactor.GetCharactersUseCase
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.ui.model.CharacterUI
import avila.daniel.rickmorty.ui.model.mapper.CharacterUIMapper
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val characterUIMapper: CharacterUIMapper,
    private val scheduleProvider: IScheduleProvider
) : BaseViewModel() {

    val charactersLiveData = SingleLiveEvent<Resource<List<CharacterUI>?>>()

    private var isLoading = false

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (!isLoading) {
            if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
                isLoading = true
                loadMoreCharacteres()
            }
        }
    }

    fun loadMoreCharacteres() {
        charactersLiveData.value = Resource.loading()
        addDisposable(getCharactersUseCase.execute()
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({ characters ->
                charactersLiveData.value =
                    Resource.success(characters?.run { characterUIMapper.map(this) })
                isLoading = false
            }) {
                isLoading = false
                charactersLiveData.value = Resource.error(it.localizedMessage)
            })
    }
}
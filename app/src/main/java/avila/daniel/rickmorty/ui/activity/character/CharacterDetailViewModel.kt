package avila.daniel.rickmorty.ui.activity.character

import avila.daniel.domain.interactor.AddCharacterToFavoriteUseCase
import avila.daniel.domain.interactor.IsFavoriteUseCase
import avila.daniel.domain.interactor.RemoveCharacterFromFavoriteUseCase
import avila.daniel.domain.model.Character
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.CharacterParcelable
import avila.daniel.rickmorty.ui.model.mapper.CharacterParcelableMapper
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharacterDetailViewModel(
    private val addCharacterToFavoriteUseCase: AddCharacterToFavoriteUseCase,
    private val removeCharacterFromFavoriteUseCase: RemoveCharacterFromFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val characterParcelableMapper: CharacterParcelableMapper,
    private val scheduleProvider: IScheduleProvider
) : BaseViewModel() {

    val characterLive = SingleLiveEvent<Character>()
    val isFavoriteLiveData = SingleLiveEvent<Boolean>()

    fun getCharacter(characterParcelable: CharacterParcelable) {
        loadingState()
        characterLive.value = characterParcelableMapper.inverseMap(characterParcelable)
        addDisposable(isFavoriteUseCase.execute(characterLive.value!!.id)
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io()).subscribe({ isFavorite ->
                isFavoriteLiveData.value = isFavorite
                successState()
            }) {
                errorState(it.localizedMessage)
            })
    }

    fun addFavorite() {
        loadingState()
        addDisposable(addCharacterToFavoriteUseCase.execute(characterLive.value!!)
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({
                isFavoriteLiveData.value = true
                successState()
            }) {
                errorState(it.localizedMessage)
            })
    }

    fun removeFavorite() {
        loadingState()
        addDisposable(removeCharacterFromFavoriteUseCase.execute(characterLive.value!!.id)
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({
                isFavoriteLiveData.value = false
                successState()
            }) {
                errorState(it.localizedMessage)
            })
    }
}
package avila.daniel.rickmorty.ui.activity.character

import avila.daniel.domain.interactor.AddFavoriteCharacterUseCase
import avila.daniel.domain.interactor.IsCharacterFavoriteUseCase
import avila.daniel.domain.interactor.RemoveFavoriteCharacterUseCase
import avila.daniel.domain.model.Character
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.CharacterParcelable
import avila.daniel.rickmorty.ui.model.mapper.CharacterParcelableMapper
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharacterDetailViewModel(
    private val addFavoriteCharacterUseCase: AddFavoriteCharacterUseCase,
    private val removeFavoriteCharacterUseCase: RemoveFavoriteCharacterUseCase,
    private val isCharacterFavoriteUseCase: IsCharacterFavoriteUseCase,
    private val characterParcelableMapper: CharacterParcelableMapper,
    private val scheduleProvider: IScheduleProvider
) : BaseViewModel() {

    val characterLive = SingleLiveEvent<Character>()
    val isFavoriteLiveData = SingleLiveEvent<Boolean>()

    fun getCharacter(characterParcelable: CharacterParcelable) {
        loadingState()
        characterLive.value = characterParcelableMapper.inverseMap(characterParcelable)
        addDisposable(isCharacterFavoriteUseCase.execute(characterLive.value!!.id)
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
        addDisposable(addFavoriteCharacterUseCase.execute(characterLive.value!!)
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
        addDisposable(removeFavoriteCharacterUseCase.execute(characterLive.value!!.id)
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
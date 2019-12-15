package avila.daniel.rickmorty.ui.activity.character

import avila.daniel.domain.interactor.AddCharacterToFavoriteUseCase
import avila.daniel.domain.interactor.IsFavoriteUseCase
import avila.daniel.domain.interactor.RemoveCharacterFromFavoriteUseCase
import avila.daniel.domain.model.Character
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.CharacterParcelable
import avila.daniel.rickmorty.ui.model.mapper.CharacterParcelableMapper
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharacterViewModel(
    private val addCharacterToFavoriteUseCase: AddCharacterToFavoriteUseCase,
    private val removeCharacterFromFavoriteUseCase: RemoveCharacterFromFavoriteUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val characterParcelableMapper: CharacterParcelableMapper,
    private val scheduleProvider: IScheduleProvider
) : BaseViewModel() {

    val characterLiveData = SingleLiveEvent<Resource<Character>>()
    val favoriteLiveData = SingleLiveEvent<Resource<Boolean>>()
    val isFavoriteLiveData = SingleLiveEvent<Resource<Boolean>>()

    fun getCharacter(characterParcelable: CharacterParcelable) {
        characterLiveData.value = Resource.loading()
        val character = characterParcelableMapper.inverseMap(characterParcelable)
        Resource.success(character)

        addDisposable(isFavoriteUseCase.execute(character.id)
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io()).subscribe({ isFavorite ->
                isFavoriteLiveData.value = Resource.success(isFavorite)
            }) {

            })

    }

    fun addFavorite(character: Character) {
        favoriteLiveData.value = Resource.loading()
        addDisposable(addCharacterToFavoriteUseCase.execute(character)
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({
                favoriteLiveData.value = Resource.success(true)
            }) {
                favoriteLiveData.value = Resource.error(it.localizedMessage)
            })
    }

    fun removeFavorite(id: Int) {
        favoriteLiveData.value = Resource.loading()
        addDisposable(removeCharacterFromFavoriteUseCase.execute(id)
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({
                favoriteLiveData.value = Resource.success(false)
            }) {
                favoriteLiveData.value = Resource.error(it.localizedMessage)
            })
    }

}
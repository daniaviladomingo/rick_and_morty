package avila.daniel.rickmorty.ui.activity.charactersfrom

import avila.daniel.domain.interactor.GetCharactersFavoriteUseCase
import avila.daniel.domain.interactor.GetEpisodeCharactersUseCase
import avila.daniel.domain.interactor.GetLocationCharactersUseCase
import avila.daniel.domain.model.Character
import avila.daniel.repository.cache.model.compose.CharacterSearchFilter
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.CharacterParcelable
import avila.daniel.rickmorty.ui.model.mapper.CharacterParcelableMapper
import avila.daniel.rickmorty.ui.util.IViewModelManagementCharacter
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharactersFromViewModel(
    private val getLocationCharactersUseCase: GetLocationCharactersUseCase,
    private val getEpisodeCharactersUseCase: GetEpisodeCharactersUseCase,
    private val getCharactersFavoriteUseCase: GetCharactersFavoriteUseCase,
    private val characterParcelableMapper: CharacterParcelableMapper,
    private val searchFilter: () -> CharacterSearchFilter,
    private val scheduleProvider: IScheduleProvider
) : BaseViewModel(), IViewModelManagementCharacter {

    val items = SingleLiveEvent<List<Character>>().apply { value = emptyList() }
    val characterParcelableLiveData = SingleLiveEvent<CharacterParcelable>()

    fun loadCharactersFromEpisode(id: Int) {
        loadingState()
        addDisposable(getEpisodeCharactersUseCase.execute(id)
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({ characters ->
                items.value = characters
                successState()
            }) {
                errorState(it.localizedMessage)
            })
    }

    fun loadCharactersFromLocation(id: Int) {
        loadingState()
        addDisposable(getLocationCharactersUseCase.execute(id)
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({ characters ->
                items.value = characters
                successState()
            }) {
                errorState(it.localizedMessage)
            })
    }

    fun loadCharactersFromFavorite() {
        loadingState()
        addDisposable(
            getCharactersFavoriteUseCase.execute()
                .observeOn(scheduleProvider.ui())
                .subscribeOn(scheduleProvider.io())
                .subscribe({ characters ->
                    items.value = characters
                    successState()
                }) {
                    errorState(it.localizedMessage)
                })
    }

    override fun openDetail(character: Character) {
        characterParcelableLiveData.value = characterParcelableMapper.map(character)
    }

    override fun searchFilter(): CharacterSearchFilter = searchFilter.invoke()
}
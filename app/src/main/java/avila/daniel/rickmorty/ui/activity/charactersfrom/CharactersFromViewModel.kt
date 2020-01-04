package avila.daniel.rickmorty.ui.activity.charactersfrom

import androidx.lifecycle.MutableLiveData
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
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharactersFromViewModel(
    private val getLocationCharactersUseCase: GetLocationCharactersUseCase,
    private val getEpisodeCharactersUseCase: GetEpisodeCharactersUseCase,
    private val getCharactersFavoriteUseCase: GetCharactersFavoriteUseCase,
    private val characterParcelableMapper: CharacterParcelableMapper,
    private val searchFilter: () -> CharacterSearchFilter,
    private val scheduleProvider: IScheduleProvider
) : BaseViewModel(), IViewModelManagementCharacter {

    val items = MutableLiveData<List<Character>>().apply { value = emptyList() }
    val charactersLiveData = SingleLiveEvent<Resource<List<Void>>>()
    val characterParcelableLiveData = SingleLiveEvent<Resource<CharacterParcelable>>()

    fun loadCharactersFromEpisode(id: Int) {
        charactersLiveData.value = Resource.loading()
        addDisposable(getEpisodeCharactersUseCase.execute(id)
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({ characters ->
                charactersLiveData.value = Resource.loadingFinish()
                items.value = characters
            }) {
                charactersLiveData.value = Resource.error(it.localizedMessage)
            })
    }

    fun loadCharactersFromLocation(id: Int) {
        charactersLiveData.value = Resource.loading()
        addDisposable(getLocationCharactersUseCase.execute(id)
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({ characters ->
                charactersLiveData.value = Resource.loadingFinish()
                items.value = characters
            }) {
                charactersLiveData.value = Resource.error(it.localizedMessage)
            })
    }

    fun loadCharactersFromFavorite() {
        charactersLiveData.value = Resource.loading()
        addDisposable(
            getCharactersFavoriteUseCase.execute()
                .observeOn(scheduleProvider.ui())
                .subscribeOn(scheduleProvider.io())
                .subscribe({ characters ->
                    charactersLiveData.value = Resource.loadingFinish()
                    items.value = characters
                }) {
                    charactersLiveData.value = Resource.error(it.localizedMessage)
                })
    }

    override fun openDetail(character: Character) {
        characterParcelableLiveData.value =
            Resource.success(characterParcelableMapper.map(character))
    }

    override fun searchFilter(): CharacterSearchFilter = searchFilter.invoke()
}
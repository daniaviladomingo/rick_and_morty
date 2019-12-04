package avila.daniel.rickmorty.ui.fragment.characters

import android.util.Log
import avila.daniel.domain.interactor.GetCharactersFilterSettingsUseCase
import avila.daniel.domain.interactor.GetCharactersUseCase
import avila.daniel.domain.model.ParameterCharacter
import avila.daniel.domain.model.settings.compose.CharacterFilterGenderParameter
import avila.daniel.domain.model.settings.compose.CharacterFilterParameter
import avila.daniel.domain.model.settings.compose.CharacterFilterStatusParameter
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.CharacterUI
import avila.daniel.rickmorty.ui.model.mapper.CharacterUIMapper
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.util.SingleLiveEvent

class CharactersViewModel(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getCharactersFilterSettingsUseCase: GetCharactersFilterSettingsUseCase,
    private val characterUIMapper: CharacterUIMapper,
    private val scheduleProvider: IScheduleProvider
) : BaseViewModel() {

    private var totalPages = 1

    private var sendParameterFilter = ParameterCharacter(
        1,
        "",
        CharacterFilterStatusParameter.ANY,
        "",
        "",
        CharacterFilterGenderParameter.ANY
    )

    val charactersLiveData = SingleLiveEvent<Resource<List<CharacterUI>?>>()

    private var isLoading = false

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (!isLoading) {
            if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
                isLoading = true
                Log.d("fff", "loadmore")
                loadMoreCharacteres()
            }
        }
    }

    fun loadMoreCharacteres() {
        if (sendParameterFilter.page <= totalPages) {
            charactersLiveData.value = Resource.loading()
            Log.d("fff", "${sendParameterFilter.page}")
            addDisposable(getCharactersUseCase.execute(sendParameterFilter)
                .observeOn(scheduleProvider.ui())
                .subscribeOn(scheduleProvider.io())
                .subscribe({ characters ->
                    charactersLiveData.value =
                        Resource.success(characters.second?.run { characterUIMapper.map(this) })
                    sendParameterFilter.page++
                    totalPages = characters.first
                    isLoading = false
                }) {
                    isLoading = false
                    charactersLiveData.value = Resource.error(it.localizedMessage)
                })
        }
    }

    fun searchFilter(text: String) {
        sendParameterFilter.page = 0
        addDisposable(getCharactersFilterSettingsUseCase.execute()
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({ settings ->
                settings.run {
                    when (this.parameterFilter) {
                        CharacterFilterParameter.NAME -> sendParameterFilter.name = text
                        CharacterFilterParameter.SPECIES -> sendParameterFilter.species = text
                        CharacterFilterParameter.TYPE -> sendParameterFilter.type = text
                    }

                    sendParameterFilter.status = this.status
                    sendParameterFilter.gender = this.gender

                    loadMoreCharacteres()
                }
            }) {})
    }
}
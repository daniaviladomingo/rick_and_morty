package avila.daniel.rickmorty.ui.fragment.episodes

import avila.daniel.domain.interactor.GetEpisodesUseCase
import avila.daniel.rickmorty.base.BaseViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.EpisodeUI
import avila.daniel.rickmorty.ui.model.mapper.EpisodeUIMapper
import avila.daniel.rickmorty.ui.util.data.Resource
import avila.daniel.rickmorty.util.SingleLiveEvent

class EpisodesViewModel(
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val episodesUIMapper: EpisodeUIMapper,
    private val scheduleProvider: IScheduleProvider,
    private val initialPage: Int
) : BaseViewModel() {

    val episodesLiveData = SingleLiveEvent<Resource<Pair<Boolean,List<EpisodeUI>>>>()

    private var isLoading = false
    private var currentPage = initialPage
    private var currentSearchFilter = ""
    private var clear = false

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (!isLoading) {
            if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
                isLoading = true
                loadMoreEpisodes()
            }
        }
    }

    fun loadMoreEpisodes() {
        episodesLiveData.value = Resource.loading()
        addDisposable(getEpisodesUseCase.execute(Pair(currentSearchFilter, currentPage))
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({ episodes ->
                episodesLiveData.value =
                    Resource.success(Pair(clear, episodesUIMapper.map(episodes)))
                clear = false
                currentPage++
                isLoading = false
            }) {
                isLoading = false
                episodesLiveData.value = Resource.error(it.localizedMessage)
            })

    }

    fun updateSearchFilter(newSearchFilter: String) {
        if (newSearchFilter != currentSearchFilter) {
            currentSearchFilter = newSearchFilter
            clearNReload()
        }
    }

    private fun clearNReload() {
        clear = true
        currentPage = initialPage
        loadMoreEpisodes()
    }
}
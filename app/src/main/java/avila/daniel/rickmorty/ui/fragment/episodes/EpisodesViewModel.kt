package avila.daniel.rickmorty.ui.fragment.episodes

import android.util.Log
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
    private val scheduleProvider: IScheduleProvider
) : BaseViewModel() {

    private var currentPage = 1
    private var totalPages = 1

    val episodesLiveData = SingleLiveEvent<Resource<List<EpisodeUI>?>>()

    private var isLoading = false

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (!isLoading) {
            if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
                isLoading = true
                loadMoreEpisodes()
            }
        }
    }

    fun loadMoreEpisodes() {
        Log.d("fff", "$currentPage $totalPages")
        if (currentPage <= totalPages) {
            episodesLiveData.value = Resource.loading()
            addDisposable(getEpisodesUseCase.execute(currentPage)
                .observeOn(scheduleProvider.ui())
                .subscribeOn(scheduleProvider.io())
                .subscribe({ episodes ->
                    episodesLiveData.value =
                        Resource.success(episodes.second?.run { episodesUIMapper.map(this) })
                    currentPage++
                    totalPages = episodes.first
                    isLoading = false
                }) {
                    isLoading = false
                    episodesLiveData.value = Resource.error(it.localizedMessage)
                })
        }
    }

    fun searchFilter(text: String) {

    }
}
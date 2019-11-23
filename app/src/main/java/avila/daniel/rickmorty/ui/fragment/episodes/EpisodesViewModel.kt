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
    private val scheduleProvider: IScheduleProvider
) : BaseViewModel() {

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
        episodesLiveData.value = Resource.loading()
        addDisposable(getEpisodesUseCase.execute()
            .observeOn(scheduleProvider.ui())
            .subscribeOn(scheduleProvider.io())
            .subscribe({ characters ->
                episodesLiveData.value =
                    Resource.success(characters?.run { episodesUIMapper.map(this) })
            }) {
                episodesLiveData.value = Resource.error(it.localizedMessage)
            })
    }
}
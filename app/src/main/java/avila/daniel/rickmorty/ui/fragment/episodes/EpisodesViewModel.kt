package avila.daniel.rickmorty.ui.fragment.episodes

import avila.daniel.domain.interactor.GetEpisodesUseCase
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.base.PaginationViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.EpisodeUI
import avila.daniel.rickmorty.ui.model.mapper.EpisodeUIMapper

class EpisodesViewModel(
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val episodesUIMapper: EpisodeUIMapper,
    scheduleProvider: IScheduleProvider,
    initialPage: Int
) : PaginationViewModel<Episode, EpisodeUI>(scheduleProvider, initialPage) {
    override fun query(): SingleUseCaseWithParameter<Pair<String, Int>, Pair<Int, List<Episode>>> =
        getEpisodesUseCase

    override fun mapper(): Mapper<Episode, EpisodeUI>? = episodesUIMapper

//    val episodesLiveData = SingleLiveEvent<Resource<Pair<Boolean,List<EpisodeUI>>>>()
//
//    private var isLoading = false
//    private var currentPage = initialPage
//    private var totalPage = initialPage
//    private var currentSearchFilter = ""
//    private var clear = false
//
//    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
//        if (!isLoading) {
//            if (visibleItemCount + lastVisibleItemPosition >= totalItemCount) {
//                isLoading = true
//                loadMoreEpisodes()
//            }
//        }
//    }
//
//    fun loadMoreEpisodes() {
//        if (currentPage <= totalPage) {
//            episodesLiveData.value = Resource.loading()
//            addDisposable(getEpisodesUseCase.execute(Pair(currentSearchFilter, currentPage))
//                .observeOn(scheduleProvider.ui())
//                .subscribeOn(scheduleProvider.io())
//                .subscribe({ episodes ->
//                    episodesLiveData.value =
//                        Resource.success(Pair(clear, episodesUIMapper.map(episodes.second)))
//                    clear = false
//                    currentPage++
//                    isLoading = false
//                }) {
//                    isLoading = false
//                    episodesLiveData.value = Resource.error(it.localizedMessage)
//                })
//        }
//
//    }
//
//    fun updateSearchFilter(newSearchFilter: String) {
//        if (newSearchFilter != currentSearchFilter) {
//            currentSearchFilter = newSearchFilter
//            clearNReload()
//        }
//    }
//
//    private fun clearNReload() {
//        clear = true
//        currentPage = initialPage
//        totalPage = initialPage
//        loadMoreEpisodes()
//    }
}
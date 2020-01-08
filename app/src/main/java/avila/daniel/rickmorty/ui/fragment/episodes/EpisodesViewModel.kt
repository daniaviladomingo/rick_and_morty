package avila.daniel.rickmorty.ui.fragment.episodes

import avila.daniel.domain.interactor.GetEpisodesUseCase
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Episode
import avila.daniel.domain.model.mapper.Mapper
import avila.daniel.rickmorty.base.PaginationViewModel
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.ui.model.EpisodeUI
import avila.daniel.rickmorty.ui.model.mapper.EpisodeUIMapper
import avila.daniel.rickmorty.util.SingleLiveEvent

class EpisodesViewModel(
    private val getEpisodesUseCase: GetEpisodesUseCase,
    private val episodesUIMapper: EpisodeUIMapper,
    scheduleProvider: IScheduleProvider,
    initialPage: Int
) : PaginationViewModel<Episode, EpisodeUI>(scheduleProvider, initialPage) {
    val navigateCharactersEpisodeLiveData = SingleLiveEvent<Pair<Int, String>>()

    override fun query(): SingleUseCaseWithParameter<Pair<String, Int>, Pair<Int, List<Episode>>> =
        getEpisodesUseCase

    override fun mapper(): Mapper<Episode, EpisodeUI>? = episodesUIMapper

    fun navigateToCharactersEpisode(id: Int, name: String) {
        navigateCharactersEpisodeLiveData.value = Pair(id, name)
    }
}
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
}
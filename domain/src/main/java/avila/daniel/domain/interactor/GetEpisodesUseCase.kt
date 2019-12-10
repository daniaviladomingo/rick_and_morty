package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Episode
import io.reactivex.Single

class GetEpisodesUseCase(private val repository: IRepository) :
    SingleUseCaseWithParameter<Pair<String, Int>, Pair<Int, List<Episode>>> {
    override fun execute(parameter: Pair<String, Int>): Single<Pair<Int, List<Episode>>> =
        repository.getEpisodes(parameter.first, parameter.second)
}
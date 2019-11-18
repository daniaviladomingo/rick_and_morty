package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCase
import avila.daniel.domain.model.Episode
import io.reactivex.Single

class GetEpisodesUseCase(private val repository: IRepository): SingleUseCase<List<Episode>?> {
    override fun execute(): Single<List<Episode>?> = repository.getEpisodes()
}
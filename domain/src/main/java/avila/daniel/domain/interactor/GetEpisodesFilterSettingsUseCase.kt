package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCase
import avila.daniel.domain.model.settings.EpisodeFilterSettings
import io.reactivex.Single

class GetEpisodesFilterSettingsUseCase(private val repository: IRepository): SingleUseCase<EpisodeFilterSettings> {
    override fun execute(): Single<EpisodeFilterSettings> = repository.getEpisodeFilterSettings()
}
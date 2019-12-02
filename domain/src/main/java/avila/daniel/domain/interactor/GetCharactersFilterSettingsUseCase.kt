package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCase
import avila.daniel.domain.model.settings.CharactersFilterSettings
import io.reactivex.Single

class GetCharactersFilterSettingsUseCase(private val repository: IRepository): SingleUseCase<CharactersFilterSettings> {
    override fun execute(): Single<CharactersFilterSettings> = repository.getCharactersFilterSettings()
}
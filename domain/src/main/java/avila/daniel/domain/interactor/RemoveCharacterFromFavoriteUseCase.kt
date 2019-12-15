package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.CompletableUseCaseWithParameter
import avila.daniel.domain.interactor.type.SingleUseCase
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Character
import io.reactivex.Completable
import io.reactivex.Single

class RemoveCharacterFromFavoriteUseCase(private val repository: IRepository) :
    CompletableUseCaseWithParameter<Int> {
    override fun execute(parameter: Int): Completable =
        repository.removeCharacterFavorite(parameter)
}
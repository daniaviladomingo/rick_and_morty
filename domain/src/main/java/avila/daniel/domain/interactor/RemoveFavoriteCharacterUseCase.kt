package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.CompletableUseCaseWithParameter
import io.reactivex.Completable

class RemoveFavoriteCharacterUseCase(private val repository: IRepository) :
    CompletableUseCaseWithParameter<Int> {
    override fun execute(parameter: Int): Completable =
        repository.removeFavoriteCharacter(parameter)
}
package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.CompletableUseCaseWithParameter
import avila.daniel.domain.interactor.type.SingleUseCase
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Character
import io.reactivex.Completable
import io.reactivex.Single

class AddCharacterToFavoriteUseCase(private val repository: IRepository) :
    CompletableUseCaseWithParameter<Character> {
    override fun execute(parameter: Character): Completable =
        repository.addCharacterFavorite(parameter)
}
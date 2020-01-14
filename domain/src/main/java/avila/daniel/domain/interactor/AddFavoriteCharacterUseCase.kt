package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.CompletableUseCaseWithParameter
import avila.daniel.domain.model.Character
import io.reactivex.Completable

class AddFavoriteCharacterUseCase(private val repository: IRepository) :
    CompletableUseCaseWithParameter<Character> {
    override fun execute(parameter: Character): Completable =
        repository.addFavoriteCharacter(parameter)
}
package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Character
import io.reactivex.Single

class GetCharacterUseCase(private val repository: IRepository) :
    SingleUseCaseWithParameter<Int, Character> {
    override fun execute(parameter: Int): Single<Character> =
        repository.getCharacter(parameter)
}
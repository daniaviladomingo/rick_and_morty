package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.ParameterCharacter
import io.reactivex.Single

class GetCharactersUseCase(private val repository: IRepository) :
    SingleUseCaseWithParameter<ParameterCharacter, Pair<Int, List<Character>?>> {
    override fun execute(parameter: ParameterCharacter): Single<Pair<Int, List<Character>?>> =
        repository.getCharacters(parameter)
}
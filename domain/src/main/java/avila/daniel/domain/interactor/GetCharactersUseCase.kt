package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Character
import io.reactivex.Single

class GetCharactersUseCase(private val repository: IRepository) :
    SingleUseCaseWithParameter<Pair<String, Int>, List<Character>> {
    override fun execute(parameter: Pair<String, Int>): Single<List<Character>> =
        repository.getCharacters(parameter.first, parameter.second)
}
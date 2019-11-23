package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import avila.daniel.domain.model.Character
import io.reactivex.Single

class GetEpisodeCharactersUseCase(private val repository: IRepository) :
    SingleUseCaseWithParameter<Int, List<Character>> {
    override fun execute(parameter: Int): Single<List<Character>> =
        repository.getEpisodeCharacters(parameter)
}
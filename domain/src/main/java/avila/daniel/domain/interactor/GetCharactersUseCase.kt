package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCase
import avila.daniel.domain.model.Character
import io.reactivex.Single

class GetCharactersUseCase(private val repository: IRepository): SingleUseCase<List<Character>?> {
    override fun execute(): Single<List<Character>?> = repository.getCharacters()
}
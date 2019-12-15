package avila.daniel.domain.interactor

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.type.SingleUseCaseWithParameter
import io.reactivex.Single

class IsFavoriteUseCase(private val repository: IRepository) :
    SingleUseCaseWithParameter<Int, Boolean> {
    override fun execute(parameter: Int): Single<Boolean> = repository.isFavorite(parameter)
}
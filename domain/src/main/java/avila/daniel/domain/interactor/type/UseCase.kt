package avila.daniel.domain.interactor.type

import io.reactivex.Observable

interface UseCase<T> {
    fun execute(): Observable<T>
}
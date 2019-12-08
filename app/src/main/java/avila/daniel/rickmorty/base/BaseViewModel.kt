package avila.daniel.rickmorty.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private val disposable = CompositeDisposable()

    override fun onCleared() {
        dispose()
        super.onCleared()
    }

    protected fun addDisposable(d: Disposable) {
        disposable.add(d)
    }

    protected fun dispose(){
        if (!disposable.isDisposed) {
            disposable.clear()
        }
    }

}

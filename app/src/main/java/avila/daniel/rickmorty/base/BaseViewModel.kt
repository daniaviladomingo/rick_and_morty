package avila.daniel.rickmorty.base

import androidx.lifecycle.ViewModel
import avila.daniel.rickmorty.ui.util.data.ResourceState
import avila.daniel.rickmorty.util.SingleLiveEvent
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel() {
    private val disposable = CompositeDisposable()

    val state = SingleLiveEvent<ResourceState>()
    val message = SingleLiveEvent<String?>()

    fun errorState(message: String?) {
        state.value = ResourceState.ERROR
        this.message.value = message
    }

    fun successState() {
        state.value = ResourceState.SUCCESS
    }

    fun emptyState() {
        state.value = ResourceState.EMPTY
    }

    fun loadingState() {
        state.value = ResourceState.LOADING
    }

    override fun onCleared() {
        dispose()
        super.onCleared()
    }

    protected fun addDisposable(d: Disposable) {
        disposable.add(d)
    }

    protected fun dispose() {
        if (!disposable.isDisposed) {
            disposable.clear()
        }
    }

}

package avila.daniel.rickmorty

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import avila.daniel.domain.ILifecycleObserver

class LifecycleManager(
    private val lifecycleObserver: Set<ILifecycleObserver>,
    lifecycle: Lifecycle
) : LifecycleObserver {
    init {
        lifecycle.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        lifecycleObserver.forEach { it.onDestroy() }
    }
}
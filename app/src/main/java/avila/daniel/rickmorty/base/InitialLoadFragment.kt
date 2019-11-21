package avila.daniel.rickmorty.base

import android.os.Bundle

abstract class InitialLoadFragment : BaseFragment() {

    private var isInitialLoad = false

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (!isInitialLoad) {
            isInitialLoad = true
            initialLoad()
        }
    }

    abstract fun initialLoad()
}
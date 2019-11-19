package avila.daniel.rickmorty.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.ui.data.ResourceState
import kotlinx.android.synthetic.main.activity_base.view_empty
import kotlinx.android.synthetic.main.activity_base.view_error
import kotlinx.android.synthetic.main.activity_base.view_progress
import kotlinx.android.synthetic.main.fragment_base.view.*
import kotlinx.android.synthetic.main.view_error.*

abstract class BaseFragment : Fragment() {

    private lateinit var fragmentView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (getLayoutId() == 0) {
            throw RuntimeException("Invalid Layout ID")
        }

        val resourceView = inflater.inflate(R.layout.fragment_base, container, false)

        fragmentView = layoutInflater.inflate(getLayoutId(), null)
        (resourceView.view as FrameLayout).addView(fragmentView, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

        resourceView.view_empty.emptyListener = checkAgain()
        resourceView.view_error.errorListener = tryAgain()

        return resourceView
    }

    protected abstract fun getLayoutId(): Int

    protected fun managementResourceState(resourceState: ResourceState, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> {
                fragmentView.visibility = VISIBLE
                view_error.visibility = GONE
                view_empty.visibility = GONE
                view_progress.visibility = VISIBLE
            }
            ResourceState.SUCCESS -> {
                fragmentView.visibility = VISIBLE
                view_error.visibility = GONE
                view_empty.visibility = GONE
                view_progress.visibility = GONE
            }
            ResourceState.EMPTY -> {
                fragmentView.visibility = GONE
                view_error.visibility = GONE
                view_empty.visibility = VISIBLE
                view_progress.visibility = GONE
            }
            ResourceState.ERROR -> {
                fragmentView.visibility = GONE
                view_error.visibility = VISIBLE
                error_message.text = message ?: ""
                view_empty.visibility = GONE
                view_progress.visibility = GONE
            }
        }
    }

    protected fun revertResourceState() {
        fragmentView.visibility = VISIBLE
        view_error.visibility = GONE
        view_empty.visibility = GONE
        view_progress.visibility = GONE
    }

    abstract fun checkAgain(): () -> Unit

    protected open fun tryAgain(): () -> Unit = { revertResourceState() }
}
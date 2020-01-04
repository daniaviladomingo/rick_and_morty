package avila.daniel.rickmorty.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.fragment_base.*
import kotlinx.android.synthetic.main.fragment_base.view.*
import kotlinx.android.synthetic.main.view_error.*

abstract class BaseFragment : Fragment() {

    protected lateinit var binding: ViewDataBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (getLayoutId() == 0) {
            throw RuntimeException("Invalid Layout ID")
        }

        val resourceView = inflater.inflate(R.layout.fragment_base, container, false)

        binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, getLayoutId(), null, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        (resourceView.view as FrameLayout).addView(binding.root, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))

        resourceView.view_empty.emptyListener = checkAgain()
        resourceView.view_error.errorListener = tryAgain()

        return resourceView
    }

    protected abstract fun getLayoutId(): Int

    protected fun managementResourceState(resourceState: ResourceState, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> {
                binding.root.visibility = VISIBLE
                view_error.visibility = GONE
                view_empty.visibility = GONE
                view_progress.visibility = VISIBLE
            }
            ResourceState.LOADING_FINISH -> {
                binding.root.visibility = VISIBLE
                view_error.visibility = GONE
                view_empty.visibility = GONE
                view_progress.visibility = GONE
            }
            ResourceState.SUCCESS -> {
                binding.root.visibility = VISIBLE
                view_error.visibility = GONE
                view_empty.visibility = GONE
                view_progress.visibility = GONE
            }
            ResourceState.EMPTY -> {
                binding.root.visibility = GONE
                view_error.visibility = GONE
                view_empty.visibility = VISIBLE
                view_progress.visibility = GONE
            }
            ResourceState.ERROR -> {
                binding.root.visibility = GONE
                view_error.visibility = VISIBLE
                error_message.text = message ?: ""
                view_empty.visibility = GONE
                view_progress.visibility = GONE
            }
        }
    }

    protected fun revertResourceState() {
        this.binding.root.visibility = VISIBLE
        view_error.visibility = GONE
        view_empty.visibility = GONE
        view_progress.visibility = GONE
    }

    abstract fun checkAgain(): () -> Unit

    abstract fun tryAgain(): () -> Unit
}
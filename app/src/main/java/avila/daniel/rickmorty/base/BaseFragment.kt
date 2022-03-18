package avila.daniel.rickmorty.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.databinding.ViewBaseBindingBinding
import kotlinx.android.synthetic.main.view_base.view.*

abstract class BaseFragment : Fragment() {

    protected lateinit var binding: ViewDataBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        if (getLayoutId() == 0) {
            throw RuntimeException("Invalid Layout ID")
        }

        val resourceView = vm()?.let {
            DataBindingUtil.inflate<ViewBaseBindingBinding>(
                layoutInflater,
                R.layout.view_base_binding,
                null,
                false
            )
                .apply {
                    lifecycleOwner = this@BaseFragment.viewLifecycleOwner
                    viewModel = it
                }.root
        } ?: inflater.inflate(R.layout.view_base, container, false)


        binding =
            DataBindingUtil.inflate(layoutInflater, getLayoutId(), null, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        (resourceView.view as FrameLayout).addView(
            binding.root,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )

        resourceView.view_empty.emptyListener = checkAgain()
        resourceView.view_error.errorListener = tryAgain()

        return resourceView
    }

    protected abstract fun getLayoutId(): Int

    abstract fun vm(): BaseViewModel?

    abstract fun checkAgain(): () -> Unit

    abstract fun tryAgain(): () -> Unit
}
package avila.daniel.rickmorty.base

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.ui.util.data.ResourceState
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.view_error.*

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getLayoutId() == 0) {
            throw RuntimeException("Invalid Layout ID")
        }

        setContentView(R.layout.activity_base)

        binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, getLayoutId(), null, false)
        binding.lifecycleOwner = this

        (view as FrameLayout).addView(
            binding.root,
            LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
        )

        view_empty.emptyListener = checkAgain()
        view_error.errorListener = tryAgain()
    }

    protected abstract fun getLayoutId(): Int

    protected fun managementResourceState(resourceState: ResourceState, message: String?) {
        when (resourceState) {
            ResourceState.LOADING -> {
                view.visibility = VISIBLE
                view_error.visibility = GONE
                view_empty.visibility = GONE
                view_progress.visibility = VISIBLE
            }
            ResourceState.LOADING_FINISH -> {
                view.visibility = VISIBLE
                view_error.visibility = GONE
                view_empty.visibility = GONE
                view_progress.visibility = GONE
            }
            ResourceState.SUCCESS -> {
                view.visibility = VISIBLE
                view_error.visibility = GONE
                view_empty.visibility = GONE
                view_progress.visibility = GONE
            }
            ResourceState.EMPTY -> {
                view.visibility = GONE
                view_error.visibility = GONE
                view_empty.visibility = VISIBLE
                view_progress.visibility = GONE
            }
            ResourceState.ERROR -> {
                view.visibility = GONE
                view_error.visibility = VISIBLE
                error_message.text = message ?: ""
                view_empty.visibility = GONE
                view_progress.visibility = GONE
            }
        }
    }

    abstract fun checkAgain(): () -> Unit

    abstract fun tryAgain(): () -> Unit
}
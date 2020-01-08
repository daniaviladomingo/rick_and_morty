package avila.daniel.rickmorty.base

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.databinding.ActivityBaseBindingBinding
import kotlinx.android.synthetic.main.activity_base_binding.*

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getLayoutId() == 0) {
            throw RuntimeException("Invalid Layout ID")
        }

        vm()?.let {
            setContentView<ActivityBaseBindingBinding>(this, R.layout.activity_base_binding).apply {
                lifecycleOwner = this@BaseActivity
                viewModel = it
            }
        } ?: setContentView(R.layout.activity_base)

        binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, getLayoutId(), null, false)
        binding.lifecycleOwner = this

        (view as FrameLayout).addView(
            binding.root,
            LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT) as ViewGroup.LayoutParams
        )

        view_empty.emptyListener = checkAgain()
        view_error.errorListener = tryAgain()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    protected abstract fun getLayoutId(): Int

//    protected fun managementResourceState(resourceState: ResourceState, message: String?) {
//        when (resourceState) {
//            ResourceState.LOADING -> {
//                view.visibility = VISIBLE
//                view_error.visibility = GONE
//                view_empty.visibility = GONE
//                view_progress.visibility = VISIBLE
//            }
////            ResourceState.LOADING_FINISH -> {
////                view.visibility = VISIBLE
////                view_error.visibility = GONE
////                view_empty.visibility = GONE
////                view_progress.visibility = GONE
////            }
//            ResourceState.SUCCESS -> {
//                view.visibility = VISIBLE
//                view_error.visibility = GONE
//                view_empty.visibility = GONE
//                view_progress.visibility = GONE
//            }
//            ResourceState.EMPTY -> {
//                view.visibility = GONE
//                view_error.visibility = GONE
//                view_empty.visibility = VISIBLE
//                view_progress.visibility = GONE
//            }
//            ResourceState.ERROR -> {
//                view.visibility = GONE
//                view_error.visibility = VISIBLE
//                error_message.text = message ?: "An error has occurred"
//                view_empty.visibility = GONE
//                view_progress.visibility = GONE
//            }
//        }
//    }

    abstract fun vm(): BaseViewModel?

    abstract fun checkAgain(): () -> Unit

    abstract fun tryAgain(): () -> Unit
}
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
import avila.daniel.rickmorty.databinding.ViewBaseBindingBinding
import kotlinx.android.synthetic.main.view_base_binding.*

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var binding: ViewDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (getLayoutId() == 0) {
            throw RuntimeException("Invalid Layout ID")
        }

        vm()?.let {
            setContentView<ViewBaseBindingBinding>(this, R.layout.view_base_binding).apply {
                lifecycleOwner = this@BaseActivity
                viewModel = it
            }
        } ?: setContentView(R.layout.view_base)

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

    abstract fun vm(): BaseViewModel?

    abstract fun checkAgain(): () -> Unit

    abstract fun tryAgain(): () -> Unit
}
package avila.daniel.rickmorty.ui.util.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import avila.daniel.rickmorty.R
import kotlinx.android.synthetic.main.view_error.view.*

class ErrorView : RelativeLayout {

    var errorListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr) {
//        initStyle(attrs, defStyleAttr)
        init()
    }

//    private fun initStyle(attrs: AttributeSet, defStyleAttr: Int) {
//        init()
//
//        val typedArray = context.obtainStyledAttributes(
//            attrs,
//            R.styleable.ErrorView,
//            defStyleAttr,
//            0
//        )
//
//        if (typedArray.hasValue(R.styleable.ErrorView_message)) {
//            error_message.text = typedArray.getString(R.styleable.ErrorView_message)
//        }
//
//
//        typedArray.recycle()
//    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_error, this)
        button_try_again.setOnClickListener { errorListener?.invoke() }
    }

    fun setMessage(message: String?) {
        error_message.text = message ?: "An error has occurred"
    }
}
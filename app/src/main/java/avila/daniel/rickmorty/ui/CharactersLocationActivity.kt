package avila.daniel.rickmorty.ui

import android.os.Bundle
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity

class CharactersLocationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun getLayoutId(): Int = R.layout.activity_characters

    override fun checkAgain(): () -> Unit = {}

    override fun tryAgain(): () -> Unit = {}
}

package avila.daniel.rickmorty.ui

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.base.BaseActivity
import kotlinx.android.synthetic.main.activity_location.*

class CharacterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list_characters.layoutManager = GridLayoutManager(this, 3)

    }

    override fun getLayoutId(): Int = R.layout.activity_character

    override fun checkAgain(): () -> Unit = {}

    override fun tryAgain(): () -> Unit = {}
}

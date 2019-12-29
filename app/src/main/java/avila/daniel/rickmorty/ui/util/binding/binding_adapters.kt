package avila.daniel.rickmorty.ui.util.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import avila.daniel.domain.model.Character
import avila.daniel.repository.cache.model.compose.CharacterFilter
import com.bumptech.glide.Glide


@BindingAdapter("imageUrl")
fun bindImageUrl(view: ImageView, imageUrl: String?) {
    imageUrl?.run {
        Glide.with(view).load(imageUrl).into(view)
    }
}

@BindingAdapter("character", "characterFilter")
fun bindTitleCharacter(
    textView: TextView,
    character: Character,
    characterFilter: () -> CharacterFilter
) {
    textView.text =
        when (characterFilter()) {
            CharacterFilter.NAME -> character.name
            CharacterFilter.SPECIES -> character.species
            CharacterFilter.TYPE -> character.type
        }.run { if (length > 20) substring(0..20) + "..." else if (this.isEmpty()) "???" else this }
}

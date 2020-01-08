package avila.daniel.rickmorty.ui.util.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import avila.daniel.domain.model.Character
import avila.daniel.domain.model.compose.Status
import avila.daniel.repository.cache.model.compose.CharacterSearchFilter
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.ui.util.IDataSet
import avila.daniel.rickmorty.ui.util.custom.ErrorView
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun bindImageUrl(imageView: ImageView, imageUrl: String?) {
    imageUrl?.run {
        Glide.with(imageView).load(imageUrl).into(imageView)
    }
}

@BindingAdapter("character", "characterFilter")
fun bindTitleCharacter(
    textView: TextView,
    character: Character,
    searchFilter: CharacterSearchFilter
) {
    textView.text =
        when (searchFilter) {
            CharacterSearchFilter.NAME -> character.name
            CharacterSearchFilter.SPECIES -> character.species
            CharacterSearchFilter.TYPE -> character.type
        }.run { if (length > 20) substring(0..20) + "..." else if (this.isEmpty()) "???" else this }
}


@BindingAdapter("status")
fun bindStatus(imageView: ImageView, status: Status) {
    imageView.setImageResource(
        when (status) {
            Status.ALIVE -> R.drawable.ic_alive
            Status.DEAD -> R.drawable.ic_dead
            else -> R.drawable.ic_unknown
        }
    )
}

@BindingAdapter("location")
fun bindLocation(view: View, location: String) {
    if (view is TextView) {
        if (location == "unknown") {
            view.visibility = View.GONE
        } else {
            view.text = location
            view.visibility = View.VISIBLE
        }
    } else if (view is ImageView) {
        if (location == "unknown") {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.GONE
        }
    }
}

@BindingAdapter("items")
fun <T> setItems(recyclerView: RecyclerView, items: List<T>) {
    (recyclerView.adapter as IDataSet<T>).setData(items)
}


@BindingAdapter("message")
fun message(errorView: ErrorView, message: String?) {
    errorView.setMessage(message)
}
package avila.daniel.rickmorty.ui.model.compose

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OriginParcelable(
    val name: String,
    val url: String
): Parcelable
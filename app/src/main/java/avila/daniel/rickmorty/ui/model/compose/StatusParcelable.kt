package avila.daniel.rickmorty.ui.model.compose

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class StatusParcelable : Parcelable {
    ALIVE,
    DEAD,
    UNKNOWN
}


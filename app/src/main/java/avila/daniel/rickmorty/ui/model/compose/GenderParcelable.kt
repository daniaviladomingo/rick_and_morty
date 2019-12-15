package avila.daniel.rickmorty.ui.model.compose

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
enum class GenderParcelable: Parcelable {
    MALE,
    FEMALE,
    GENDERLESS,
    UNKNOWN
}

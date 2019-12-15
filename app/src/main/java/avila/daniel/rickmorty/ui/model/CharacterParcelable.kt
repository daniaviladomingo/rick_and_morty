package avila.daniel.rickmorty.ui.model

import android.os.Parcelable
import avila.daniel.domain.model.Location
import avila.daniel.domain.model.compose.Gender
import avila.daniel.domain.model.compose.Status
import avila.daniel.rickmorty.ui.model.compose.LocationParcelable
import avila.daniel.rickmorty.ui.model.compose.OriginParcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterParcelable(
    val created: String,
    val episode: List<String>,
    val gender: Gender,
    val id: Int,
    val image: String,
    val location: LocationParcelable,
    val name: String,
    val origin: OriginParcelable,
    val species: String,
    val status: Status,
    val type: String,
    val url: String
): Parcelable
package avila.daniel.rickmorty.ui.model

import android.os.Parcelable
import avila.daniel.rickmorty.ui.model.compose.GenderParcelable
import avila.daniel.rickmorty.ui.model.compose.LocationParcelable
import avila.daniel.rickmorty.ui.model.compose.OriginParcelable
import avila.daniel.rickmorty.ui.model.compose.StatusParcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterParcelable(
    val created: String,
    val episode: List<String>,
    val gender: GenderParcelable,
    val id: Int,
    val image: String,
    val location: LocationParcelable,
    val name: String,
    val origin: OriginParcelable,
    val species: String,
    val status: StatusParcelable,
    val type: String,
    val url: String
): Parcelable
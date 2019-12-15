package avila.daniel.rickmorty.ui.model

import android.os.Parcel
import android.os.Parcelable

enum class CharactersSource : Parcelable {
    LOCATION,
    EPISODE,
    FAVORITES;

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharactersSource> {
        override fun createFromParcel(parcel: Parcel): CharactersSource {

            return values()[parcel.readInt()]
        }

        override fun newArray(size: Int): Array<CharactersSource?> {
            return arrayOfNulls(size)
        }
    }
}
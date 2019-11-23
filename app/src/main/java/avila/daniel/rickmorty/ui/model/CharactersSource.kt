package avila.daniel.rickmorty.ui.model

import android.os.Parcel
import android.os.Parcelable

enum class CharactersSource() : Parcelable {
    LOCATION,
    EPISODE;

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharactersSource> {
        override fun createFromParcel(parcel: Parcel): CharactersSource {
            return values()[parcel.readInt()-1]
        }

        override fun newArray(size: Int): Array<CharactersSource?> {
            return arrayOfNulls(size)
        }
    }
}
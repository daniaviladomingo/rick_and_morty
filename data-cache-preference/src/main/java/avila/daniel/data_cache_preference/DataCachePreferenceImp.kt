package avila.daniel.data_cache_preference

import android.content.SharedPreferences
import avila.daniel.data_cache.preference.IDataCachePreference
import avila.daniel.domain.model.settings.CharactersFilterSettings
import avila.daniel.domain.model.settings.EpisodeFilterSettings
import avila.daniel.domain.model.settings.LocationFilterSettings
import io.reactivex.Single

class DataCachePreferenceImp(
    private val sharedPreferences: SharedPreferences,
    private val keyNameCharacter: String,
    private val keyStatus: String,
    private val keySpecie: String,
    private val keyTypeCharacter: String,
    private val keyGender: String,
    private val keyNameLocation: String,
    private val keyTypeLocation: String,
    private val keyNameEpisode: String
) : IDataCachePreference {

    override fun getCharacterFilter(): Single<CharactersFilterSettings> = Single.create {
        it.onSuccess(
            CharactersFilterSettings(
                sharedPreferences.getBoolean(keyNameCharacter, true),
                sharedPreferences.getString(keyStatus, "")!!,
                sharedPreferences.getBoolean(keySpecie, true),
                sharedPreferences.getBoolean(keyTypeCharacter, true),
                sharedPreferences.getString(keyGender, "")!!
            )
        )
    }

    override fun getLocationFilter(): Single<LocationFilterSettings> = Single.create {
        it.onSuccess(
            LocationFilterSettings(
                sharedPreferences.getBoolean(keyNameLocation, true),
                sharedPreferences.getBoolean(keyTypeLocation, true)
            )
        )
    }

    override fun getEpisodeFilter(): Single<EpisodeFilterSettings> = Single.create {
        it.onSuccess(
            EpisodeFilterSettings(sharedPreferences.getBoolean(keyNameEpisode, true))
        )
    }
}
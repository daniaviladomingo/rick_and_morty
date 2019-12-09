package avila.daniel.data_cache_preference

import android.content.SharedPreferences
import avila.daniel.data_cache.preference.IDataCachePreference
import avila.daniel.data_cache_preference.model.mapper.PreferenceGenderMapper
import avila.daniel.data_cache_preference.model.mapper.PreferenceStatusMapper
import avila.daniel.domain.model.settings.CharactersFilterSettings
import avila.daniel.domain.model.settings.LocationFilterSettings
import avila.daniel.domain.model.settings.compose.CharacterFilterParameter
import io.reactivex.Single

class DataCachePreferenceImp(
    private val defaultSharedPreferences: SharedPreferences,
    private val keyNameCharacter: String,
    private val keyStatus: String,
    private val keySpecie: String,
    private val keyTypeCharacter: String,
    private val keyGender: String,
    private val keyNameLocation: String,
    private val keyTypeLocation: String,
    private val keyDimension: String,
    private val preferenceGenderMapper: PreferenceGenderMapper,
    private val preferenceStatusMapper: PreferenceStatusMapper
) : IDataCachePreference {

    override fun getCharacterFilter(): Single<CharactersFilterSettings> = Single.create {
        it.onSuccess(
            CharactersFilterSettings(
                when {
                    defaultSharedPreferences.getBoolean(keyNameCharacter, true) -> {
                        CharacterFilterParameter.NAME
                    }
                    defaultSharedPreferences.getBoolean(keySpecie, true) -> {
                        CharacterFilterParameter.SPECIES
                    }
                    defaultSharedPreferences.getBoolean(keyTypeCharacter, true) -> {
                        CharacterFilterParameter.TYPE
                    }
                    else -> {
                        CharacterFilterParameter.NAME
                    }
                },
                preferenceStatusMapper.map(defaultSharedPreferences.getString(keyStatus, "") ?: ""),
                preferenceGenderMapper.map(defaultSharedPreferences.getString(keyGender, "") ?: "")
            )
        )
    }

    override fun getLocationFilter(): Single<LocationFilterSettings> = Single.create {
        it.onSuccess(
            when {
                defaultSharedPreferences.getBoolean(keyNameLocation, true) -> {
                    LocationFilterSettings.NAME
                }
                defaultSharedPreferences.getBoolean(keyDimension, true) -> {
                    LocationFilterSettings.DIMENSION
                }
                defaultSharedPreferences.getBoolean(keyTypeLocation, true) -> {
                    LocationFilterSettings.TYPE
                }
                else -> {
                    LocationFilterSettings.NAME
                }
            }
        )
    }
}
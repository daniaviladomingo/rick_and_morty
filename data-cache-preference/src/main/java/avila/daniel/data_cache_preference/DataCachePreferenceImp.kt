package avila.daniel.data_cache_preference

import android.content.SharedPreferences
import avila.daniel.data_cache.preference.IDataCachePreference
import avila.daniel.data_cache_preference.model.mapper.PreferenceGenderMapper
import avila.daniel.data_cache_preference.model.mapper.PreferenceStatusMapper
import avila.daniel.domain.model.settings.CharactersFilterSettings
import avila.daniel.domain.model.settings.LocationFilterParameter
import avila.daniel.domain.model.settings.compose.CharacterFilterParameter
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
    private val keyDimension: String,
    private val preferenceGenderMapper: PreferenceGenderMapper,
    private val preferenceStatusMapper: PreferenceStatusMapper
) : IDataCachePreference {

    override fun getCharacterFilter(): Single<CharactersFilterSettings> = Single.create {
        it.onSuccess(
            CharactersFilterSettings(
                when {
                    sharedPreferences.getBoolean(keyNameCharacter, true) -> {
                        CharacterFilterParameter.NAME
                    }
                    sharedPreferences.getBoolean(keySpecie, true) -> {
                        CharacterFilterParameter.SPECIES
                    }
                    sharedPreferences.getBoolean(keyTypeCharacter, true) -> {
                        CharacterFilterParameter.TYPE
                    }
                    else -> {
                        CharacterFilterParameter.NAME
                    }
                },
                preferenceStatusMapper.map(sharedPreferences.getString(keyStatus, "") ?: ""),
                preferenceGenderMapper.map(sharedPreferences.getString(keyGender, "") ?: "")
            )
        )
    }

    override fun getLocationFilter(): Single<LocationFilterParameter> = Single.create {
        it.onSuccess(
            when {
                sharedPreferences.getBoolean(keyNameLocation, true) -> {
                    LocationFilterParameter.NAME
                }
                sharedPreferences.getBoolean(keyDimension, true) -> {
                    LocationFilterParameter.DIMENSION
                }
                sharedPreferences.getBoolean(keyTypeLocation, true) -> {
                    LocationFilterParameter.TYPE
                }
                else -> {
                    LocationFilterParameter.NAME
                }
            }
        )
    }
}
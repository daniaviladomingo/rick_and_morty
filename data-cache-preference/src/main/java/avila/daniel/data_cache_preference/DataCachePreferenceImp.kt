package avila.daniel.data_cache_preference

import android.content.SharedPreferences
import avila.daniel.data_cache.preference.IDataCachePreference
import avila.daniel.data_cache_preference.model.mapper.PreferenceGenderMapper
import avila.daniel.data_cache_preference.model.mapper.PreferenceStatusMapper
import avila.daniel.repository.cache.model.CharactersFilterSettings
import avila.daniel.repository.cache.model.LocationFilterParameter
import avila.daniel.repository.cache.model.compose.CharacterFilter
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

    fun getCharacterSearchFilter(): CharacterFilter =
        when {
            defaultSharedPreferences.getBoolean(keyNameCharacter, true) -> {
                CharacterFilter.NAME
            }
            defaultSharedPreferences.getBoolean(keySpecie, true) -> {
                CharacterFilter.SPECIES
            }
            defaultSharedPreferences.getBoolean(keyTypeCharacter, true) -> {
                CharacterFilter.TYPE
            }
            else -> {
                CharacterFilter.NAME
            }
        }

    fun getLocationSearchFilter(): LocationFilterParameter = when {
        defaultSharedPreferences.getBoolean(keyNameLocation, true) -> {
            LocationFilterParameter.NAME
        }
        defaultSharedPreferences.getBoolean(keyDimension, true) -> {
            LocationFilterParameter.DIMENSION
        }
        defaultSharedPreferences.getBoolean(keyTypeLocation, true) -> {
            LocationFilterParameter.TYPE
        }
        else -> {
            LocationFilterParameter.NAME
        }
    }

    override fun getCharacterFilter(): Single<CharactersFilterSettings> = Single.create {
        it.onSuccess(
            CharactersFilterSettings(
                getCharacterSearchFilter(),
                preferenceStatusMapper.map(defaultSharedPreferences.getString(keyStatus, "") ?: ""),
                preferenceGenderMapper.map(defaultSharedPreferences.getString(keyGender, "") ?: "")
            )
        )
    }

    override fun getLocationFilter(): Single<LocationFilterParameter> = Single.create {
        it.onSuccess(getLocationSearchFilter())
    }
}
package avila.daniel.data_cache_preference

import android.content.SharedPreferences
import avila.daniel.domain.model.settings.CharactersFilterSettings
import avila.daniel.domain.model.settings.EpisodeFilterSettings
import avila.daniel.domain.model.settings.LocationFilterSettings
import avila.daniel.repository.cache.preference.IDataCachePreference
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
    override fun getCharacterFilter(): Single<CharactersFilterSettings> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLocationFilter(): Single<LocationFilterSettings> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEpisodeFilter(): Single<EpisodeFilterSettings> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
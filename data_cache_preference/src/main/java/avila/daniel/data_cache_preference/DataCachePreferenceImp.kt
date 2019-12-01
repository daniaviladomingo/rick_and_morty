package avila.daniel.data_cache_preference

import android.content.SharedPreferences
import avila.daniel.repository.cache.preference.IDataCachePreference

class DataCachePreferenceImp(
    private val sharedPreferences: SharedPreferences
) : IDataCachePreference {

}
package avila.daniel.rickmorty.di

import androidx.lifecycle.Lifecycle
import androidx.preference.PreferenceManager
import avila.daniel.data_cache.DataCacheImp
import avila.daniel.data_cache.preference.IDataCachePreference
import avila.daniel.data_cache_preference.DataCachePreferenceImp
import avila.daniel.domain.ILifecycleObserver
import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.*
import avila.daniel.repository.RepositoryImp
import avila.daniel.repository.cache.IDataCache
import avila.daniel.repository.remote.IDataRemote
import avila.daniel.repository.remote.model.mapper.CharacterApiMapper
import avila.daniel.repository.remote.model.mapper.EpisodeApiMapper
import avila.daniel.rickmorty.BuildConfig
import avila.daniel.rickmorty.LifecycleManager
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.di.qualifiers.*
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.schedulers.ScheduleProviderImp
import avila.daniel.rickmorty.ui.CharactersLocationViewModel
import avila.daniel.rickmorty.ui.fragment.characters.CharactersViewModel
import avila.daniel.rickmorty.ui.fragment.episodes.EpisodesViewModel
import avila.daniel.rickmorty.ui.fragment.locations.LocationsViewModel
import avila.daniel.rickmorty.ui.model.mapper.CharacterUIMapper
import avila.daniel.rickmorty.ui.model.mapper.EpisodeUIMapper
import avila.daniel.rickmorty.ui.model.mapper.LocationUIMapper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

val appModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }
}

val activityModule = module {
    factory { (lifecycle: Lifecycle) ->
        LifecycleManager(get(), lifecycle)
        Unit
    }
}

val viewModelModule = module {
    viewModel { CharactersViewModel(get(), get(), get(), get()) }
    viewModel { LocationsViewModel(get(), get(), get()) }
    viewModel { EpisodesViewModel(get(), get(), get()) }
    viewModel { CharactersLocationViewModel(get(), get(), get(), get(), get()) }
}

val useCaseModule = module {
    single { GetCharactersUseCase(get()) }
    single { GetLocationsUseCase(get()) }
    single { GetLocationCharactersUseCase(get()) }
    single { GetEpisodesUseCase(get()) }
    single { GetEpisodeCharactersUseCase(get()) }
    single { GetCharactersFilterSettingsUseCase(get()) }
    single { GetLocationsFilterSettingsUseCase(get()) }
    single { GetEpisodesFilterSettingsUseCase(get()) }
}

val repositoryModule = module {
    single<IRepository> {
        RepositoryImp(
            get(),
            get(),
            get(InitialPageCharacters),
            get(InitialPageLocation),
            get(InitialPageEpisodes),
            get(),
            get()
        )
    } bind ILifecycleObserver::class

    single(InitialPageCharacters) { 1 }
    single(InitialPageLocation) { 1 }
    single(InitialPageEpisodes) { 1 }
}

val dataRemoteModule = module {
    single<IDataRemote> {
        Retrofit.Builder()
            .baseUrl(get(EndPoint) as String)
            .client(get())
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
            .create(IDataRemote::class.java)
    }

    single<CallAdapter.Factory> { RxJava2CallAdapterFactory.create() }

    single<Converter.Factory> { GsonConverterFactory.create() }

    single {
        OkHttpClient()
            .newBuilder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE
                )
            )
            .build()
    }

    single(EndPoint) { "https://rickandmortyapi.com/api/" }
}

val dataCacheModule = module {
    single<IDataCache> {
        DataCacheImp(get())
    }
}

val dataCachePreferenceModule = module {
    single<IDataCachePreference> {
        DataCachePreferenceImp(
            get(),
            get(KeyNameCharacter),
            get(KeyStatus),
            get(KeySpecie),
            get(KeyTypeCharacter),
            get(KeyGender),
            get(KeyNameLocation),
            get(KeyTypeLocation),
            get(KeyNameEpisode)
        )
    }

    single(KeyNameCharacter) {
        androidContext().getString(R.string.key_name_characters)
    }

    single(KeyStatus) {
        androidContext().getString(R.string.key_status)
    }

    single(KeySpecie) {
        androidContext().getString(R.string.key_specie)
    }

    single(KeyTypeCharacter) {
        androidContext().getString(R.string.key_type_characters)
    }

    single(KeyGender) {
        androidContext().getString(R.string.key_gender)
    }

    single(KeyNameLocation) {
        androidContext().getString(R.string.key_name_location)
    }

    single(KeyTypeLocation) {
        androidContext().getString(R.string.key_type_location)
    }

    single(KeyNameEpisode) {
        androidContext().getString(R.string.key_name_episode)
    }
}

val scheduleModule = module {
    single<IScheduleProvider> { ScheduleProviderImp() }
}

val mapperModule = module {
    single { CharacterUIMapper(get(MaxLenghtName)) }
    single { LocationUIMapper() }
    single { EpisodeUIMapper(get(RangeSeason), get(RangeEpisode)) }

    single { CharacterApiMapper() }
    single { EpisodeApiMapper() }

    single(RangeSeason) { (1..2) }
    single(RangeEpisode) { (4..5) }

    single(MaxLenghtName) { 20 }
}

val dateFormatModule = module {
    single {
        SimpleDateFormat(get(DatePattern), Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
    single(DatePattern) { "dd MMM yyyy" }
}
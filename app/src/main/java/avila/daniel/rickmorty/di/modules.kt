package avila.daniel.rickmorty.di

import androidx.lifecycle.Lifecycle
import androidx.preference.PreferenceManager
import androidx.room.Room
import avila.daniel.data_cache.DataCacheImp
import avila.daniel.data_cache.db.IDataCacheDb
import avila.daniel.data_cache.preference.IDataCachePreference
import avila.daniel.data_cache_db.DataCacheDbImp
import avila.daniel.data_cache_db.definition.AppDatabase
import avila.daniel.data_cache_db.model.mapper.CharacterAllMapper
import avila.daniel.data_cache_db.model.mapper.CharacterDbMapper
import avila.daniel.data_cache_db.model.mapper.LocationDbMapper
import avila.daniel.data_cache_db.model.mapper.OriginDbMapper
import avila.daniel.data_cache_preference.DataCachePreferenceImp
import avila.daniel.data_cache_preference.model.mapper.PreferenceGenderMapper
import avila.daniel.data_cache_preference.model.mapper.PreferenceStatusMapper
import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.*
import avila.daniel.repository.RepositoryImp
import avila.daniel.repository.cache.IDataCache
import avila.daniel.repository.cache.model.compose.CharacterSearchFilter
import avila.daniel.repository.remote.IDataRemote
import avila.daniel.repository.remote.model.mapper.CharacterApiMapper
import avila.daniel.repository.remote.model.mapper.EpisodeApiMapper
import avila.daniel.repository.remote.model.mapper.GenderParameterMapper
import avila.daniel.repository.remote.model.mapper.StatusParameterMapper
import avila.daniel.rickmorty.BuildConfig
import avila.daniel.rickmorty.R
import avila.daniel.rickmorty.di.qualifiers.*
import avila.daniel.rickmorty.di.qualifiers.Any
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.schedulers.ScheduleProviderImp
import avila.daniel.rickmorty.ui.activity.character.CharacterDetailViewModel
import avila.daniel.rickmorty.ui.activity.charactersfrom.CharactersFromViewModel
import avila.daniel.rickmorty.ui.fragment.characters.CharactersViewModel
import avila.daniel.rickmorty.ui.fragment.episodes.EpisodesViewModel
import avila.daniel.rickmorty.ui.fragment.locations.LocationsViewModel
import avila.daniel.rickmorty.ui.model.mapper.CharacterParcelableMapper
import avila.daniel.rickmorty.ui.model.mapper.EpisodeUIMapper
import avila.daniel.rickmorty.ui.model.mapper.LocationUIMapper
import avila.daniel.rickmorty.ui.util.IDataChanged
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

lateinit var characterReload: IDataChanged

val appModule = module {
    single { PreferenceManager.getDefaultSharedPreferences(androidContext()) }

    single { CharacterSearchFilter.NAME }
}

val activityModule = module {
    factory { (lifecycle: Lifecycle) ->
        //        LifecycleManager(get(), lifecycle)
        Unit
    }
    single { characterReload }
}

val viewModelModule = module {
    viewModel {
        CharactersViewModel(
            get(),
            get(),
            get(SearchFilterCharacters),
            get(),
            get(InitialPage)
        ).apply {
            characterReload = this
        }
    }
    viewModel { LocationsViewModel(get(), get(), get(), get(InitialPage)) }
    viewModel { EpisodesViewModel(get(), get(), get(), get(InitialPage)) }
    viewModel {
        CharactersFromViewModel(
            get(),
            get(),
            get(),
            get(),
            get(SearchFilterCharacters),
            get()
        )
    }
    viewModel { CharacterDetailViewModel(get(), get(), get(), get(), get()) }

    single(InitialPage) { 1 }
}

val useCaseModule = module {
    single { GetCharactersUseCase(get()) }
    single { GetLocationsUseCase(get()) }
    single { GetLocationCharactersUseCase(get()) }
    single { GetEpisodesUseCase(get()) }
    single { GetEpisodeCharactersUseCase(get()) }
    single { AddFavoriteCharacterUseCase(get()) }
    single { RemoveFavoriteCharacterUseCase(get()) }
    single { IsCharacterFavoriteUseCase(get()) }
    single { GetFavoriteCharactersUseCase(get()) }
}

val repositoryModule = module {
    single<IRepository> {
        RepositoryImp(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
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
        DataCacheImp(get(), get())
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
            get(KeyDimension),
            get(),
            get()
        )
    }

    single(SearchFilterCharacters) {
        { ((get() as IDataCachePreference) as DataCachePreferenceImp).getCharacterSearchFilter() }
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

    single(KeyDimension) {
        androidContext().getString(R.string.key_dimension)
    }
}

val dataCacheDbModule = module {

    single<IDataCacheDb> {
        DataCacheDbImp(get(), get(), get(), get(), get())
    }

    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "RickNMorty.db").build()
    }
}

val scheduleModule = module {
    single<IScheduleProvider> { ScheduleProviderImp() }
}

val mapperModule = module {
    single { LocationUIMapper() }
    single { EpisodeUIMapper(get(RangeSeason), get(RangeEpisode)) }

    single { CharacterApiMapper() }
    single { EpisodeApiMapper() }

    single { StatusParameterMapper("alive", "dead", "unknown", "") }
    single { GenderParameterMapper("male", "female", "genderless", "unknown", "") }

    single { CharacterDbMapper() }
    single { CharacterAllMapper(get(), get()) }
    single { LocationDbMapper() }
    single { OriginDbMapper() }

    single { CharacterParcelableMapper() }

    single {
        PreferenceStatusMapper(
            get(StatusAlive),
            get(StatusDead),
            get(Unknown),
            get(Any)
        )
    }

    single {
        PreferenceGenderMapper(
            get(GenderMale),
            get(GenderFemale),
            get(GenderLess),
            get(Unknown),
            get(Any)
        )
    }

    single(StatusAlive) {
        androidContext().getString(R.string.alive)
    }

    single(StatusDead) {
        androidContext().getString(R.string.dead)
    }

    single(GenderMale) {
        androidContext().getString(R.string.male)
    }

    single(GenderFemale) {
        androidContext().getString(R.string.female)
    }

    single(GenderLess) {
        androidContext().getString(R.string.genderless)
    }

    single(Unknown) {
        androidContext().getString(R.string.unknown)
    }

    single(Any) {
        androidContext().getString(R.string.any)
    }

    single(RangeSeason) { (1..2) }
    single(RangeEpisode) { (4..5) }
}

val dateFormatModule = module {
    single {
        SimpleDateFormat(get(DatePattern), Locale.getDefault()).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
    }
    single(DatePattern) { "dd MMM yyyy" }
}
package avila.daniel.rickmorty.di

import androidx.lifecycle.Lifecycle
import avila.daniel.domain.ILifecycleObserver
import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.GetCharactersUseCase
import avila.daniel.repository.RepositoryImp
import avila.daniel.repository.remote.IDataRemote
import avila.daniel.rickmorty.BuildConfig
import avila.daniel.rickmorty.LifecycleManager
import avila.daniel.rickmorty.di.qualifiers.InitialPageCharacters
import avila.daniel.rickmorty.di.qualifiers.InitialPageEpisodes
import avila.daniel.rickmorty.di.qualifiers.InitialPageLocation
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.schedulers.ScheduleProviderImp
import avila.daniel.rickmorty.ui.characters.CharactersViewModel
import avila.daniel.rickmorty.ui.episodes.EpisodesViewModel
import avila.daniel.rickmorty.ui.locations.LocationsViewModel
import avila.daniel.rickmorty.ui.model.mapper.CharacterUIMapper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val activityModule = module {
    factory { (lifecycle: Lifecycle) ->
        LifecycleManager(get(), lifecycle)
        Unit
    }
}

val viewModelModule = module {
    viewModel { CharactersViewModel(get(), get(), get()) }
    viewModel { LocationsViewModel(get(), get(), get()) }
    viewModel { EpisodesViewModel(get(), get(), get()) }
}

val useCaseModule = module {
    single { GetCharactersUseCase(get()) }
}

val repositoryModule = module {
    single<IRepository> {
        RepositoryImp(
            get(),
            get(InitialPageCharacters),
            get(InitialPageLocation),
            get(InitialPageEpisodes)
        )
    }  bind ILifecycleObserver::class
    
    single(InitialPageCharacters) { 1 }
    single(InitialPageLocation) { 1 }
    single(InitialPageEpisodes) { 1 }

    single<IDataRemote> {
        Retrofit.Builder()
            .baseUrl(get() as String)
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

    single { "https://rickandmortyapi.com/api/" }
}

val scheduleModule = module {
    single<IScheduleProvider> { ScheduleProviderImp() }
}

val mapperModule = module {
    single { CharacterUIMapper() }
}
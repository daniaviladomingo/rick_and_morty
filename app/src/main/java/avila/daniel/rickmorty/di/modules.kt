package avila.daniel.rickmorty.di

import avila.daniel.domain.IRepository
import avila.daniel.domain.interactor.GetCharactersUseCase
import avila.daniel.repository.RepositoryImp
import avila.daniel.repository.remote.IDataRemote
import avila.daniel.rickmorty.BuildConfig
import avila.daniel.rickmorty.di.qualifiers.InitialPageCharacters
import avila.daniel.rickmorty.di.qualifiers.InitialPageEpisodes
import avila.daniel.rickmorty.di.qualifiers.InitialPageLocation
import avila.daniel.rickmorty.schedulers.IScheduleProvider
import avila.daniel.rickmorty.schedulers.ScheduleProviderImp
import avila.daniel.rickmorty.ui.characters.CharactersViewModel
import avila.daniel.rickmorty.ui.episodes.EpisodesViewModel
import avila.daniel.rickmorty.ui.model.mapper.CharacterUIMapper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val viewModelModule = module {
    viewModel {
        CharactersViewModel(get(), get(), get())
    }
}

val useCaseModule = module {
    single { GetCharactersUseCase(get()) }
}

val repositoryModule = module {
    single<IRepository> { RepositoryImp(get(), get(InitialPageCharacters), get(InitialPageLocation), get(InitialPageEpisodes)) }
    single(InitialPageCharacters) { 0 }
    single(InitialPageLocation) { 0 }
    single(InitialPageEpisodes) { 0 }

    single<IDataRemote> {
        Retrofit.Builder()
            .baseUrl(get() as String)
            .client(get())
            .addConverterFactory(get())
            .addCallAdapterFactory(get())
            .build()
            .create(IDataRemote::class.java)
    }

    single { RxJava2CallAdapterFactory.create() }

    single { GsonConverterFactory.create() }

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
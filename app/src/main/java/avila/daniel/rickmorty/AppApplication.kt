package avila.daniel.rickmorty

import android.app.Application
import avila.daniel.rickmorty.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            androidLogger()
            modules(
                activityModule,
                mapperModule,
                scheduleModule,
                repositoryModule,
                useCaseModule,
                viewModelModule,
                dateFormatModule
            )
        }
    }
}
package avila.daniel.rickmorty

import androidx.multidex.MultiDexApplication
import avila.daniel.rickmorty.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AppApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@AppApplication)
            androidLogger()
            modules(
                appModule,
                activityModule,
                adapterModule,
                mapperModule,
                scheduleModule,
                repositoryModule,
                dataRemoteModule,
                dataCacheModule,
                dataCacheDbModule,
                dataCachePreferenceModule,
                useCaseModule,
                viewModelModule,
                dateFormatModule
            )
        }
    }
}
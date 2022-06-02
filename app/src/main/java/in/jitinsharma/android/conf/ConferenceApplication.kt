package `in`.jitinsharma.android.conf

import `in`.jitinsharma.android.conf.di.conferenceModule
import `in`.jitinsharma.android.conf.sync.ConferenceUpdateWorker
import android.app.Application
import androidx.work.WorkManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConferenceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ConferenceApplication)
            modules(conferenceModule)
        }
        WorkManager.getInstance(this).enqueue(ConferenceUpdateWorker.getPeriodicRequest())
    }
}

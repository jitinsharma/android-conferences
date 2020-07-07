package `in`.jitinsharma.asg.conf

import `in`.jitinsharma.asg.conf.di.conferenceModule
import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ConferenceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ConferenceApplication)
            modules(conferenceModule)
        }
    }
}

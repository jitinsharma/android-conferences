package `in`.jitinsharma.asg.conf

import `in`.jitinsharma.asg.conf.utils.Container
import android.app.Application

class ConferenceApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Container.init(this)
    }
}

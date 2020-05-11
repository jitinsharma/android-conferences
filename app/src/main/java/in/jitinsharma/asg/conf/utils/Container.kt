package `in`.jitinsharma.asg.conf.utils

import `in`.jitinsharma.asg.conf.database.AppDatabase
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import android.content.Context

object Container {

    lateinit var appDatabase: AppDatabase
    lateinit var conferenceRepository: ConferenceRepository

    fun init(context: Context) {
        appDatabase = AppDatabase.getDatabase(context)
        conferenceRepository = ConferenceRepository(appDatabase)
    }
}

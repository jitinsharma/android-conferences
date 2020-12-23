package `in`.jitinsharma.asg.conf.sync

import `in`.jitinsharma.asg.conf.notification.NotificationHelper
import `in`.jitinsharma.asg.conf.preferences.AppPreferences
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

@KoinApiExtension
class ConferenceUpdateWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KoinComponent {

    private val repository: ConferenceRepository by inject()
    private val appPreferences: AppPreferences by inject()

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val conferenceList = repository.getConferenceDataFromNetwork()
            val storedConferenceList = repository.getConferenceDataList().first()
            val diff = conferenceList.filterNot { storedConferenceList.contains(it) }
            if (diff.isNotEmpty()) {
                appPreferences
                    .getUpdatePreference()
                    .collect { updatePreference ->
                        if (updatePreference) {
                            NotificationHelper.sendConferenceUpdateNotification(
                                applicationContext,
                                diff
                            )
                        }
                    }
            }
            repository.addConferenceDataToDB(conferenceList)
            return@withContext Result.success()
        }
    }

    companion object {
        fun getPeriodicRequest() =
            PeriodicWorkRequestBuilder<ConferenceUpdateWorker>(1, TimeUnit.DAYS).build()

        // For testing
        fun getImmediateRequest() =
            OneTimeWorkRequestBuilder<ConferenceUpdateWorker>().build()
    }
}

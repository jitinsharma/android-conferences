package `in`.jitinsharma.asg.conf.sync

import `in`.jitinsharma.asg.conf.notification.NotificationHelper
import `in`.jitinsharma.asg.conf.preferences.AppPreferences
import `in`.jitinsharma.asg.conf.repository.ConferenceRepositoryImpl
import `in`.jitinsharma.asg.conf.utils.AppDispatchers
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkerParameters
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.concurrent.TimeUnit

@KoinApiExtension
class ConferenceUpdateWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params), KoinComponent {

    private val repository: ConferenceRepositoryImpl by inject()
    private val appPreferences: AppPreferences by inject()
    private val dispatcherProvider = AppDispatchers

    override suspend fun doWork(): Result {
        return withContext(dispatcherProvider.io) {
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

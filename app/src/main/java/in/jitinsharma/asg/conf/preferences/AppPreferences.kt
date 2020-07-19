package `in`.jitinsharma.asg.conf.preferences

import android.content.Context
import androidx.datastore.preferences.PreferenceDataStoreFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import java.io.File

const val PREF_FILE = "app.preferences_pb"
const val UPDATE_PREF_KEY = "UPDATE_PREF_KEY"

class AppPreferences(context: Context) {

    private val appPreferencesStore =
        PreferenceDataStoreFactory().create(
            produceFile = {
                File(context.filesDir, PREF_FILE)
            }
        )

    suspend fun setUpdatePreference(enabled: Boolean) {
        appPreferencesStore.updateData { preferences ->
            preferences.toBuilder().setBoolean(UPDATE_PREF_KEY, enabled).build()
        }
    }

    fun getUpdatePreference(): Flow<Boolean> {
        return appPreferencesStore.data
            .map { preferences -> preferences.getBoolean(UPDATE_PREF_KEY, false) }
            .distinctUntilChanged()
    }
}

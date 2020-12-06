package `in`.jitinsharma.asg.conf.preferences

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

const val PREF_FILE = "app.preferences_pb"
val UPDATE_PREF_KEY = preferencesKey<Boolean>("UPDATE_PREF_KEY")

class AppPreferences(context: Context) {

    private val appPreferencesStore = context.createDataStore(name = PREF_FILE)

    suspend fun setUpdatePreference(enabled: Boolean) {
        appPreferencesStore.edit { preferences ->
            preferences[UPDATE_PREF_KEY] = enabled
        }
    }

    fun getUpdatePreference(): Flow<Boolean> {
        return appPreferencesStore.data
            .map { preferences -> preferences[UPDATE_PREF_KEY] ?: false }
            .distinctUntilChanged()
    }
}

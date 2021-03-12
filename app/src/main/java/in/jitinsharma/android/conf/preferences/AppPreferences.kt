package `in`.jitinsharma.android.conf.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

const val PREF_FILE = "app.preferences_pb"
val UPDATE_PREF_KEY = booleanPreferencesKey("UPDATE_PREF_KEY")

private val Context.appPreferencesStore: DataStore<Preferences> by preferencesDataStore(name = PREF_FILE)

class AppPreferences(private val context: Context) {

    suspend fun setUpdatePreference(enabled: Boolean) {
        context.appPreferencesStore.edit { preferences ->
            preferences[UPDATE_PREF_KEY] = enabled
        }
    }

    fun getUpdatePreference(): Flow<Boolean> {
        return context.appPreferencesStore.data
            .map { preferences -> preferences[UPDATE_PREF_KEY] ?: false }
            .distinctUntilChanged()
    }
}

package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.redux.actions.LoadConferences
import `in`.jitinsharma.asg.conf.redux.state.AppState
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.platform.setContent
import androidx.lifecycle.lifecycleScope
import org.koin.android.ext.android.inject
import org.koin.core.parameter.parametersOf
import org.rekotlin.Store

class MainActivity : AppCompatActivity() {

    private val store: Store<AppState> by inject { parametersOf(lifecycleScope) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        store.dispatch(LoadConferences())
        setContent {
            MaterialTheme(colors = themeColors) {
                ConferencePage(store = store)
            }
        }
    }
}

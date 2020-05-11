package `in`.jitinsharma.asg.conf.redux

import `in`.jitinsharma.asg.conf.redux.middleware.conferenceMiddleware
import `in`.jitinsharma.asg.conf.redux.reducer.appReducer
import androidx.compose.*
import org.rekotlin.BlockSubscriber
import org.rekotlin.StateType
import org.rekotlin.Store
import org.rekotlin.StoreType

val store = Store(
    reducer = ::appReducer,
    state = null,
    middleware = listOf(conferenceMiddleware)
)

@Composable
fun <T : StateType> StoreType<T>.observeAsState(): State<T?> {
    val state = state<T?> { null }
    val subscriber = BlockSubscriber<T> {
        FrameManager.framed {
            state.value = it
        }
    }
    onActive {
        subscribe(subscriber)
    }
    onDispose {
        unsubscribe(subscriber)
    }
    return state
}

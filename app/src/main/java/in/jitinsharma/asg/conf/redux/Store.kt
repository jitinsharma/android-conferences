package `in`.jitinsharma.asg.conf.redux

import `in`.jitinsharma.asg.conf.redux.middleware.ConferenceMiddleware
import `in`.jitinsharma.asg.conf.redux.reducer.appReducer
import org.rekotlin.Store

val store = Store(
    reducer = ::appReducer,
    state = null,
    middleware = listOf(ConferenceMiddleware())
)

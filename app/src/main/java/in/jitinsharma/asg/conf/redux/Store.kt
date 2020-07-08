package `in`.jitinsharma.asg.conf.redux

import `in`.jitinsharma.asg.conf.redux.state.AppState
import org.koin.java.KoinJavaComponent.getKoin
import org.rekotlin.Store

val store = getKoin().get<Store<AppState>>()

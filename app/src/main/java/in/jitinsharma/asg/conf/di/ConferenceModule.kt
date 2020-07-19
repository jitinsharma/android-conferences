package `in`.jitinsharma.asg.conf.di

import `in`.jitinsharma.asg.conf.database.AppDatabase
import `in`.jitinsharma.asg.conf.preferences.AppPreferences
import `in`.jitinsharma.asg.conf.redux.middleware.ConferenceMiddleware
import `in`.jitinsharma.asg.conf.redux.reducer.appReducer
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import kotlinx.coroutines.CoroutineScope
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module
import org.rekotlin.Store

val conferenceModule = module {
    single { AppDatabase.getDatabase(applicationContext = androidApplication()) }
    factory { ConferenceRepository(appDatabase = get()) }
    single { (coroutineScope: CoroutineScope) ->
        Store(
            state = null,
            reducer = ::appReducer,
            middleware = listOf(
                ConferenceMiddleware(
                    coroutineScope = coroutineScope,
                    conferenceRepository = get()
                )
            )
        )
    }
    single { AppPreferences(context = androidApplication()) }
}

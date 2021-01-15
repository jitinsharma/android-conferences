package `in`.jitinsharma.android.conf.di

import `in`.jitinsharma.android.conf.database.AppDatabase
import `in`.jitinsharma.android.conf.preferences.AppPreferences
import `in`.jitinsharma.android.conf.repository.ConferenceRepository
import `in`.jitinsharma.android.conf.repository.ConferenceRepositoryImpl
import `in`.jitinsharma.android.conf.viewmodel.ConferenceViewModel
import `in`.jitinsharma.android.conf.viewmodel.FilterScreenViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val conferenceModule = module {
    single { AppDatabase.getDatabase(applicationContext = androidApplication()) }
    factory<ConferenceRepository> { ConferenceRepositoryImpl(appDatabase = get()) }
    single { AppPreferences(context = androidApplication()) }
    viewModel { ConferenceViewModel(get()) }
    viewModel { FilterScreenViewModel(get()) }
}

package `in`.jitinsharma.asg.conf.di

import `in`.jitinsharma.asg.conf.database.AppDatabase
import `in`.jitinsharma.asg.conf.preferences.AppPreferences
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import `in`.jitinsharma.asg.conf.repository.ConferenceRepositoryImpl
import `in`.jitinsharma.asg.conf.viewmodel.ConferenceViewModel
import `in`.jitinsharma.asg.conf.viewmodel.FilterScreenViewModel
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

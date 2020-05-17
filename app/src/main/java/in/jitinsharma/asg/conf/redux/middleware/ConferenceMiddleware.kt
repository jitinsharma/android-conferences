package `in`.jitinsharma.asg.conf.redux.middleware

import `in`.jitinsharma.asg.conf.model.Country
import `in`.jitinsharma.asg.conf.redux.actions.DisplayConferences
import `in`.jitinsharma.asg.conf.redux.actions.DisplayCountries
import `in`.jitinsharma.asg.conf.redux.actions.LoadConferences
import `in`.jitinsharma.asg.conf.redux.actions.LoadCountries
import `in`.jitinsharma.asg.conf.redux.state.AppState
import `in`.jitinsharma.asg.conf.utils.Container
import androidx.collection.ArraySet
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.rekotlin.DispatchFunction
import org.rekotlin.Middleware

class ConferenceMiddleware : Middleware<AppState> {

    override fun invoke(
        dispatchFunction: DispatchFunction,
        appStateCallBack: () -> AppState?
    ): (DispatchFunction) -> DispatchFunction {
        return { dispatch ->
            { action ->
                when (action) {
                    is LoadConferences -> {
                        loadConferences(dispatchFunction)
                    }
                    is LoadCountries -> {
                        loadCountries(dispatchFunction)
                    }
                }
                dispatch(action)
            }
        }
    }

    private fun loadConferences(dispatchFunction: DispatchFunction) {
        val conferenceRepository = Container.conferenceRepository
        GlobalScope.launch {
            conferenceRepository.loadConferenceData()
            val conferenceDataList = conferenceRepository.getConferenceDataList()
            dispatchFunction.invoke(DisplayConferences(conferenceDataList))
        }
    }

    private fun loadCountries(dispatchFunction: DispatchFunction) {
        val conferenceRepository = Container.conferenceRepository
        GlobalScope.launch {
            val conferenceDataList = conferenceRepository.getConferenceDataList()
            val countries = conferenceDataList.mapTo(ArraySet()) { countryName ->
                Country(name = countryName.country)
            }.toList()
            dispatchFunction.invoke(DisplayCountries(countries))
        }
    }
}

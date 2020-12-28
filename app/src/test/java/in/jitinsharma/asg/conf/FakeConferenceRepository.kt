package `in`.jitinsharma.asg.conf

import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.repository.ConferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeConferenceRepository : ConferenceRepository {

    private val conferenceDataList = listOf(
        ConferenceData(
            name = "Test Conference",
            city = "Test City",
            country = "Test Country"
        )
    )

    override suspend fun loadConferenceData() {}

    override suspend fun getConferenceDataFromNetwork(): List<ConferenceData> {
        return conferenceDataList
    }

    override suspend fun addConferenceDataToDB(conferenceDataList: List<ConferenceData>) {}

    override fun getConferenceDataList(): Flow<List<ConferenceData>> {
        return flow {
            emit(conferenceDataList)
        }
    }
}
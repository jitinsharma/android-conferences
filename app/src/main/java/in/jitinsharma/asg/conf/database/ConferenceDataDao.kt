package `in`.jitinsharma.asg.conf.database

import `in`.jitinsharma.asg.conf.model.ConferenceData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ConferenceDataDao {

    @Query("SELECT * FROM conferenceData")
    fun getConferenceDataList(): Flow<List<ConferenceData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeConferenceData(vararg conferenceData: ConferenceData)

    @Query("DELETE FROM conferenceData")
    suspend fun deleteAllConferenceData()

    @Transaction
    suspend fun replaceAndStoreConferenceData(vararg conferenceData: ConferenceData) {
        deleteAllConferenceData()
        storeConferenceData(conferenceData = conferenceData)
    }
}

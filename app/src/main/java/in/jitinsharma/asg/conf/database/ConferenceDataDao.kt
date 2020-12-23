package `in`.jitinsharma.asg.conf.database

import `in`.jitinsharma.asg.conf.model.ConferenceData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ConferenceDataDao {

    @Query("SELECT * FROM conferenceData")
    fun getConferenceDataList(): Flow<List<ConferenceData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun storeConferenceData(vararg conferenceData: ConferenceData)

    @Query("DELETE FROM conferenceData")
    suspend fun deleteAllConferenceData()
}

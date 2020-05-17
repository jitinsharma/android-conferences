package `in`.jitinsharma.asg.conf.database

import `in`.jitinsharma.asg.conf.model.ConferenceData
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ConferenceData::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun conferenceDataDao(): ConferenceDataDao

    companion object {

        fun getDatabase(applicationContext: Context): AppDatabase {
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "androidconfdb"
            ).build()
        }
    }
}

package `in`.jitinsharma.asg.conf.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ConferenceData(
    @ColumnInfo var name: String = "",
    @ColumnInfo var city: String = "",
    @PrimaryKey @NonNull var id: String = "$name$city",
    @ColumnInfo var country: String = "",
    @ColumnInfo var url: String = "https://www.droidcon.com/",
    @ColumnInfo var date: String = "",
    @ColumnInfo var status: String = "Active",
    @ColumnInfo var isPast: Boolean = false,
    @ColumnInfo var isActive: Boolean = true,
    @Embedded var cfpData: CfpData? = null
) {
    data class CfpData(
        @ColumnInfo var cfpDate: String = "",
        @ColumnInfo var cfpUrl: String = "",
        @ColumnInfo var isCfpActive: Boolean = true
    )
}

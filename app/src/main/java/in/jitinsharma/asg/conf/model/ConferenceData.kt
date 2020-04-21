package `in`.jitinsharma.asg.conf.model

data class ConferenceData(
    var name: String = "",
    var city: String = "",
    var country: String = "",
    var url: String = "https://www.droidcon.com/",
    var date: String = "",
    var status: String = "Active",
    var isActive: Boolean = true,
    var cfpData: CfpData? = null
) {
    data class CfpData(
        var cfpDate: String = "",
        var cfpUrl: String = ""
    )
}

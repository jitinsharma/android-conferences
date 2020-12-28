package `in`.jitinsharma.asg.conf.repository

import `in`.jitinsharma.asg.conf.database.AppDatabase
import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.network.getHTMLData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

class ConferenceRepository(
    private val appDatabase: AppDatabase
) {

    suspend fun loadConferenceData() {
        val conferenceDataList = getConferenceDataFromNetwork()
        if (conferenceDataList.isNotEmpty()) {
            addConferenceDataToDB(conferenceDataList)
        }
    }

    suspend fun getConferenceDataFromNetwork(): List<ConferenceData> {
        try {
            val document = getHTMLData()
            val conferenceListElement =
                document.getElementsByClass("conference-list list-unstyled")[0]
            return conferenceListElement
                .childNodes()
                .filterNot { conferenceElement ->
                    (conferenceElement is TextNode) && conferenceElement.isBlank
                }.mapTo(ArrayList()) {
                    it.mapToConferenceDataModel()
                }.filterNot { conferenceData ->
                    conferenceData.isPast
                }
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun addConferenceDataToDB(conferenceDataList: List<ConferenceData>) {
        appDatabase.conferenceDataDao().run {
            replaceAndStoreConferenceData(*conferenceDataList.toTypedArray())
        }
    }

    fun getConferenceDataList(): Flow<List<ConferenceData>> =
        appDatabase.conferenceDataDao().getConferenceDataList().distinctUntilChanged()

    private fun Node.mapToConferenceDataModel(): ConferenceData {
        val conferenceDataModel = ConferenceData()
        conferenceDataModel.date = childNode(0).toString().replace("&nbsp; ", "").trim()
        conferenceDataModel.isPast = isPastDate(conferenceDataModel.date)
        conferenceDataModel.name = childNode(1).childNode(0).toString().trim()
        conferenceDataModel.url = childNode(1).attr("href")
        val location = childNode(3).childNode(0).toString()
        val locationDataArray = location.split(",")
        conferenceDataModel.country = locationDataArray.last().trim()
        conferenceDataModel.city = locationDataArray.dropLast(1).run {
            if (size > 1) {
                joinToString(",")
            } else {
                joinToString()
            }
        }.trim()
        conferenceDataModel.id = conferenceDataModel.name + conferenceDataModel.city
        if (childNodeSize() > 5) {
            conferenceDataModel.parseCfpAndStatusData(childNode(5).toString())
        }
        return conferenceDataModel
    }

    private fun ConferenceData.parseCfpAndStatusData(data: String) {
        data.lines().forEach { line ->
            when {
                line.contains("Call For Papers") -> {
                    val cfpData = ConferenceData.CfpData()
                    //TODO Try replacing matchers by parsing line with Jsoup
                    val cfpUrlMatcher =
                        Pattern.compile("href=(.*?)>", Pattern.DOTALL).matcher(line)
                    val cfpDateMatcher = Pattern.compile(">(.*?)<", Pattern.DOTALL).matcher(line)
                    while (cfpUrlMatcher.find()) {
                        val match = cfpUrlMatcher.group(1)
                        if (!match.isNullOrBlank()) {
                            cfpData.cfpUrl = match.replace("\"", "")
                        }
                    }
                    while (cfpDateMatcher.find()) {
                        val match = cfpDateMatcher.group(1)
                        if (!match.isNullOrBlank()) {
                            cfpData.cfpDate = match.replace("[^\\d-]".toRegex(), "")
                            cfpData.isCfpActive = isPastDate(cfpData.cfpDate).not()
                        }
                    }
                    this.cfpData = cfpData
                }
                line.contains("label-danger") && !line.contains("online", ignoreCase = true) -> {
                    this.isActive = false
                }
            }
        }
    }

    private fun isPastDate(date: String): Boolean {
        val currentDate = Date(System.currentTimeMillis())
        val conferenceDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date)
        return requireNotNull(conferenceDate) < currentDate
    }
}

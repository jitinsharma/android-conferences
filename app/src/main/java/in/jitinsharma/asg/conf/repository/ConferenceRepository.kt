package `in`.jitinsharma.asg.conf.repository

import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.network.getHTMLData
import org.jsoup.nodes.Node
import org.jsoup.nodes.TextNode
import java.util.regex.Pattern

class ConferenceRepository {

    //TODO Add error handling
    suspend fun getConferenceData(): List<ConferenceData> {
        val document = getHTMLData()
        val conferenceListElement = document.getElementsByClass("conference-list list-unstyled")[0]
        return conferenceListElement
            .childNodes()
            .filterNot { conferenceElement ->
                (conferenceElement is TextNode) && conferenceElement.isBlank
            }.mapTo(ArrayList()) {
                it.mapToConferenceDataModel()
            }
    }

    private fun Node.mapToConferenceDataModel(): ConferenceData {
        val conferenceDataModel = ConferenceData()
        conferenceDataModel.date = childNode(0).toString().replace("&nbsp; ", "").trim()
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
        conferenceDataModel.parseCfpAndStatusData(childNode(5).toString())
        return conferenceDataModel
    }

    private fun ConferenceData.parseCfpAndStatusData(data: String) {
        data.lines().forEach { line ->
            when {
                line.contains("Call For Papers") -> {
                    val cfpData = ConferenceData.CfpData()
                    //TODO Try replacing matchers by parsing line with Jsoup
                    val cfpUrlMatcher = Pattern.compile("a href=(.*?)>", Pattern.DOTALL).matcher(line)
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
}

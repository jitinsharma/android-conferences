package `in`.jitinsharma.asg.conf.network

import kotlinx.coroutines.coroutineScope
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

private const val baseUrl = "https://androidstudygroup.github.io/conferences/"

@Suppress("BlockingMethodInNonBlockingContext")
suspend fun getHTMLData(): Document =
    coroutineScope {
        Jsoup.connect(baseUrl).get()
    }

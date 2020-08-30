package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.R
import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.utils.ThemedPreview
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Box
import androidx.compose.foundation.Icon
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ripple.RippleIndication
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ContextAmbient
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.ui.tooling.preview.Preview
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ConferenceCard(
    modifier: Modifier = Modifier,
    conferenceData: ConferenceData
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = if (conferenceData.isActive) {
            MaterialTheme.colors.secondary
        } else {
            Color(0x4D3DDB85)
        },
        contentColor = MaterialTheme.colors.primary,
        modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically)
    ) {
        val context = ContextAmbient.current
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(Modifier.clickable(
                    indication = RippleIndication(),
                    onClick = { context.loadUrl(conferenceData.url) }), children = {
                    Text(
                        text = conferenceData.name,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                            fontSize = TextUnit.Sp(18)
                        )
                    )
                })

                Row {
                    Icon(
                        modifier = Modifier.padding(end = 4.dp),
                        asset = vectorResource(id = R.drawable.ic_baseline_calendar_today)
                    )
                    Text(
                        text = conferenceData.date,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = TextUnit.Sp(12)
                        )
                    )
                }
            }

            Row {
                Icon(
                    modifier = Modifier.padding(top = 4.dp, end = 4.dp),
                    asset = vectorResource(
                        id = R.drawable.ic_baseline_location_on
                    )
                )

                val location = if (conferenceData.city.isNotBlank()) {
                    "${conferenceData.city}, ${conferenceData.country}"
                } else {
                    conferenceData.country
                }

                Text(
                    text = location,
                    style = TextStyle(fontSize = TextUnit.Sp(12)),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            if (showCfp(conferenceData.cfpData)) {
                val cfpData = conferenceData.cfpData!!
                Box(Modifier.clickable(
                    indication = RippleIndication(),
                    onClick = { context.loadUrl(cfpData.cfpUrl) }
                ), children = {
                    Text(
                        text = annotatedString {
                            append("CFP closes on ")
                            pushStyle(
                                SpanStyle(
                                    textDecoration = TextDecoration.Underline,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            append(cfpData.cfpDate)
                        },
                        style = TextStyle(fontSize = TextUnit.Sp(12)),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                })
            }
        }
    }
}


private fun showCfp(cfpData: ConferenceData.CfpData?): Boolean {
    cfpData?.let {
        val currentDate = Date(System.currentTimeMillis())
        val cfpDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(cfpData.cfpDate)
        return requireNotNull(cfpDate) > currentDate
    } ?: return false
}

private fun Context.loadUrl(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
}

@Preview
@Composable
fun ConferenceCardPreview() {
    ThemedPreview {
        ConferenceCard(
            conferenceData = ConferenceData(
                name = "Droidcon",
                city = "New York City, NY",
                country = "USA",
                date = "2020-08-31",
                isActive = true,
                cfpData = ConferenceData.CfpData(
                    cfpDate = "2020-06-30"
                )
            )
        )
    }
}

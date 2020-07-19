package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.R
import `in`.jitinsharma.asg.conf.model.ConferenceData
import `in`.jitinsharma.asg.conf.utils.ThemedPreview
import android.content.Context
import android.content.Intent
import androidx.compose.Composable
import androidx.core.net.toUri
import androidx.ui.core.Alignment
import androidx.ui.core.ContextAmbient
import androidx.ui.core.Modifier
import androidx.ui.foundation.Box
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.clickable
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.RippleIndication
import androidx.ui.res.vectorResource
import androidx.ui.text.SpanStyle
import androidx.ui.text.TextStyle
import androidx.ui.text.annotatedString
import androidx.ui.text.font.FontWeight
import androidx.ui.text.style.TextDecoration
import androidx.ui.tooling.preview.Preview
import androidx.ui.unit.TextUnit
import androidx.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ConferenceCard(
    modifier: Modifier = Modifier,
    conferenceData: ConferenceData
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        color = if (conferenceData.isActive) {
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

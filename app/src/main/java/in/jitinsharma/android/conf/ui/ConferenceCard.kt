package `in`.jitinsharma.android.conf.ui

import `in`.jitinsharma.android.conf.R
import `in`.jitinsharma.android.conf.model.ConferenceData
import `in`.jitinsharma.android.conf.utils.ThemedPreview
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
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
        val context = LocalContext.current
        Column(modifier = Modifier.padding(all = 16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    Modifier.clickable(
                        onClick = { context.loadUrl(conferenceData.url) })
                ) {
                    Text(
                        text = conferenceData.name,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    )
                }

                Row {
                    Icon(
                        modifier = Modifier.padding(end = 4.dp),
                        painter = painterResource(id = R.drawable.ic_baseline_calendar_today),
                        contentDescription = null
                    )
                    Text(
                        text = conferenceData.date,
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            Row {
                Icon(
                    modifier = Modifier.padding(top = 4.dp, end = 4.dp),
                    painter = painterResource(
                        id = R.drawable.ic_baseline_location_on
                    ),
                    contentDescription = null
                )

                val location = if (conferenceData.city.isNotBlank()) {
                    "${conferenceData.city}, ${conferenceData.country}"
                } else {
                    conferenceData.country
                }

                Text(
                    text = location,
                    style = TextStyle(fontSize = 12.sp),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            if (showCfp(conferenceData.cfpData)) {
                val cfpData = conferenceData.cfpData!!
                Box(Modifier.clickable(
                    onClick = { context.loadUrl(cfpData.cfpUrl) }
                )) {
                    Text(
                        text = buildAnnotatedString {
                            append("CFP closes on ")
                            pushStyle(
                                SpanStyle(
                                    textDecoration = TextDecoration.Underline,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            append(cfpData.cfpDate)
                        },
                        style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
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

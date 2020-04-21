package `in`.jitinsharma.asg.conf.ui

import `in`.jitinsharma.asg.conf.R
import `in`.jitinsharma.asg.conf.model.ConferenceData
import androidx.compose.Composable
import androidx.ui.core.Alignment
import androidx.ui.core.Modifier
import androidx.ui.foundation.Clickable
import androidx.ui.foundation.Icon
import androidx.ui.foundation.Text
import androidx.ui.foundation.shape.corner.RoundedCornerShape
import androidx.ui.graphics.Color
import androidx.ui.layout.*
import androidx.ui.material.Card
import androidx.ui.material.MaterialTheme
import androidx.ui.material.ripple.ripple
import androidx.ui.res.vectorResource
import androidx.ui.text.AnnotatedString
import androidx.ui.text.SpanStyle
import androidx.ui.text.TextStyle
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
    conferenceData: ConferenceData,
    onTitleClicked: (url: String) -> Unit,
    onCfpClicked: ((url: String) -> Unit)? = null
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        color = if (conferenceData.isActive) {
            Color(0xFF3DDB85)
        } else {
            Color(0x4D3DDB85)
        },
        contentColor = Color(0xFF092432),
        modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Clickable(
                    onClick = { onTitleClicked(conferenceData.url) },
                    modifier = Modifier.ripple()
                ) {
                    Text(
                        text = conferenceData.name,
                        style = TextStyle(
                            textDecoration = TextDecoration.Underline,
                            fontWeight = FontWeight.Bold,
                            fontSize = TextUnit.Sp(18)
                        )
                    )
                }

                Row {
                    Icon(
                        modifier = Modifier.padding(end = 4.dp),
                        asset = vectorResource(
                            id = R.drawable.ic_baseline_calendar_today
                        )
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

                Text(
                    text = "${conferenceData.city}, ${conferenceData.country}",
                    style = TextStyle(
                        fontSize = TextUnit.Sp(12)
                    ),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            if(showCfp(conferenceData.cfpData)) {
                val cfpData = conferenceData.cfpData!!
                Clickable(modifier = Modifier.ripple(), onClick = { onCfpClicked?.invoke(cfpData.cfpUrl) }) {
                    Text(
                        text = AnnotatedString {
                            append("CFP closes on ")
                            pushStyle(
                                SpanStyle(
                                    textDecoration = TextDecoration.Underline,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            append(cfpData.cfpDate)
                        },
                        style = TextStyle(
                            fontSize = TextUnit.Sp(12)
                        ),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }
}


private fun showCfp(cfpData: ConferenceData.CfpData?): Boolean{
    cfpData?.let {
        val currentDate = Date(System.currentTimeMillis())
        val cfpDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(cfpData.cfpDate)
        return requireNotNull(cfpDate) > currentDate
    } ?: return false
}

@Preview
@Composable
fun ConferenceCardPreview() {
    MaterialTheme {
        ConferenceCard(
            conferenceData = ConferenceData(
                name = "Droidcon",
                city = "New York City, NY",
                country = "USA",
                date = "2020-08-31",
                isActive = false,
                cfpData = ConferenceData.CfpData(
                    cfpDate = "2020-06-30"
                )
            ),
            onTitleClicked = {}
        )
    }
}
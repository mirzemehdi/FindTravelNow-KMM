package ui.screens.top5flights

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.FlightInfo
import ui.components.FlightInfoItem
import ui.theme.Orange_alpha_12
import ui.theme.strings.Strings

@Composable
fun Top5FlightsScreen() {

    val uiState by remember { mutableStateOf(Top5FlightsUiState()) }

    Column(modifier = Modifier.fillMaxSize()) {
        FLightsListUI(
            modifier = Modifier.weight(1f).padding(horizontal = 30.dp, vertical = 10.dp),
            uiState.flights
        )
        BottomInfoSection(modifier = Modifier.fillMaxWidth())
    }


}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomInfoSection(modifier: Modifier) {
    Column(
        modifier = modifier.background(Orange_alpha_12)
            .padding(start = 35.dp, top = 20.dp, bottom = 14.dp, end = 35.dp)
    ) {
        Text(
            modifier = Modifier,
            text = buildAnnotatedString {
                append(Strings.origin)
                withStyle(
                    style = SpanStyle(fontWeight = FontWeight.SemiBold)
                ) { // AnnotatedString.Builder
                    append("Budapest,Hungary(BUD)")
                }

            },
            style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            ),
            textAlign = TextAlign.Start,
            color = Color.Black
        )

        FlowRow(
            modifier = Modifier.padding(top = 3.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = buildAnnotatedString {
                    append(Strings.last_update)
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) { // AnnotatedString.Builder
                        append("2023-09-25")
                    }

                },
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Start,
                color = Color.Black
            )
            Text(
                modifier = Modifier,
                text = buildAnnotatedString {
                    append(Strings.next_update)
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.SemiBold)
                    ) { // AnnotatedString.Builder
                        append("6 Days later")
                    }

                },
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                ),
                textAlign = TextAlign.Start,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun FLightsListUI(modifier: Modifier = Modifier, flights: List<FlightInfo>) {
    LazyColumn(modifier = modifier) {
        items(flights) {
            FlightInfoItem(
                flightInfo = it,
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp)
            )
        }
    }
}




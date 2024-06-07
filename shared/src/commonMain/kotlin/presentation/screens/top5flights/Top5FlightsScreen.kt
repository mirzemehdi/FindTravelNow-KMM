package presentation.screens.top5flights

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.FlightInfo
import domain.model.FlightLocation
import domain.model.FlightSort
import presentation.components.FlightInfoItem
import presentation.components.MyAppCircularProgressIndicator
import presentation.theme.Alabaster
import presentation.theme.Orange_alpha_12
import presentation.theme.strings.Strings
import util.asState

@Composable
fun Top5FlightsScreen(
    uiStateHolder: Top5FlightsUiStateHolder,
    onNavigateFlightInfo: (FlightInfo) -> Unit,
) {
    val uiState by uiStateHolder.uiState.asState()
    Top5FlightsScreen(
        uiState = uiState,
        onSelectSort = uiStateHolder::onSelectSort,
        onNavigateFlightInfo = onNavigateFlightInfo
    )
}

@Composable
private fun Top5FlightsScreen(
    uiState: Top5FlightsUiState,
    onSelectSort: (FlightSort) -> Unit,
    onNavigateFlightInfo: (FlightInfo) -> Unit,
) {


    Column(modifier = Modifier.fillMaxSize()) {
        SortByDropDown(
            modifier = Modifier.padding(end = 30.dp, top = 20.dp),
            sortBy = uiState.sortBy,
            onSelectSort = onSelectSort
        )

        Box(modifier = Modifier.fillMaxSize()) {
            if (uiState.isLoading)
                MyAppCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            else {

                Column(modifier = Modifier.fillMaxSize()) {
                    FLightsListUI(
                        modifier = Modifier.weight(1f)
                            .padding(horizontal = 30.dp, vertical = 10.dp),
                        flights = uiState.flights,
                        onClickFlightItem = onNavigateFlightInfo
                    )
                    BottomInfoSection(
                        modifier = Modifier.fillMaxWidth(),
                        origin = uiState.origin,
                        lastUpdateDate = uiState.lastUpdateDate,
                        nextUpdateInDays = uiState.nextUpdateInDays
                    )
                }
            }

        }


    }


}

@Composable
private fun SortByDropDown(
    modifier: Modifier,
    sortBy: FlightSort? = null,
    onSelectSort: (FlightSort) -> Unit,
) {
    val shape = RoundedCornerShape(5.dp)
    var isExpanded by remember { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
            .clip(shape)
            .border(width = 1.dp, color = Alabaster, shape = shape)
            .background(color = Color(0xFFFFFFFF), shape = shape)
            .clickable {
                isExpanded = !isExpanded
            }
            .padding(start = 13.dp, top = 8.dp, bottom = 8.dp, end = 4.dp)
    ) {
        Text(
            modifier = Modifier.padding(end = 44.dp),
            text = when (sortBy) {
                FlightSort.BY_DATE -> Strings.date
                FlightSort.BY_PRICE -> Strings.price
                null -> Strings.sort_by
            },
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Start,
            color = Color.Black
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowDown,
            contentDescription = null,
            tint = Color.Black
        )

        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = { isExpanded = false },
            modifier = Modifier.background(Color.White)
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier.padding(end = 44.dp),
                        text = Strings.price,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                        color = Color.Black
                    )
                },
                onClick = {
                    isExpanded = false
                    onSelectSort(FlightSort.BY_PRICE)
                }
            )
            HorizontalDivider(color = Alabaster)
            DropdownMenuItem(
                text = {
                    Text(
                        modifier = Modifier.padding(end = 44.dp),
                        text = Strings.date,
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                        color = Color.Black
                    )
                },
                onClick = {
                    isExpanded = false
                    onSelectSort(FlightSort.BY_DATE)
                }
            )
        }


    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun BottomInfoSection(
    origin: FlightLocation,
    modifier: Modifier = Modifier,
    lastUpdateDate: String = "",
    nextUpdateInDays: Int = 0,
) {
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
                    append("${origin.city}, ${origin.country}(${origin.iataCode})")
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
                        append(lastUpdateDate)
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
                    ) {

                        val textDaysLater = if (nextUpdateInDays > 1) Strings.n_days_later
                        else Strings.one_day_later
                        append("$nextUpdateInDays $textDaysLater")

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
private fun FLightsListUI(
    modifier: Modifier = Modifier,
    flights: List<FlightInfo>,
    onClickFlightItem: (FlightInfo) -> Unit,
) {
    LazyColumn(modifier = modifier) {
        items(flights) {
            FlightInfoItem(
                flightInfo = it,
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                onClickItem = { onClickFlightItem(it) }
            )
        }
    }
}




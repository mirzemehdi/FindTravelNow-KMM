package ui.screens.top5flights

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.FlightInfo
import ui.components.FlightInfoItem

@Composable
fun Top5FlightsScreen() {

    FlightInfoItem(flightInfo = FlightInfo(), modifier = Modifier.padding(20.dp))
}




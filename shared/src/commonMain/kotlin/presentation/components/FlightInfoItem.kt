package presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.model.FlightInfo
import findtravelnow_kmm.shared.generated.resources.Res
import findtravelnow_kmm.shared.generated.resources.ic_horizontal_flight
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.theme.Alabaster
import presentation.theme.Black_alpha_60
import presentation.theme.Orange_alpha_50

@Composable
fun FlightInfoItem(modifier: Modifier = Modifier, flightInfo: FlightInfo, onClickItem: () -> Unit) {
    val shape = RoundedCornerShape(11.dp)

    OutlinedCard(
        shape = shape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        border = BorderStroke(1.dp, Alabaster),
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClickItem() }
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier,
                    text = flightInfo.date,
                    style = MaterialTheme.typography.titleSmall.copy(fontSize = 17.sp),
                    textAlign = TextAlign.Start,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.weight(1f))
                Text(
                    modifier = Modifier,
                    text = flightInfo.priceWithCurrency,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    textAlign = TextAlign.Start,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
            FlightOriginDestinationInnerBox(flightInfo)
        }
    }
}

@Composable
fun FlightOriginDestinationInnerBox(flightInfo: FlightInfo) {
    val innerShape = RoundedCornerShape(6.dp)
    val innerBackgroundColor = MaterialTheme.colorScheme.secondaryContainer
    val borderColor = Orange_alpha_50

    Column(
        modifier = Modifier
            .padding(top = 4.dp)
            .fillMaxWidth()
            .border(
                width = (0.2f).dp,
                color = borderColor,
                shape = innerShape
            )
            .background(innerBackgroundColor, innerShape)
            .clip(innerShape)
            .padding(vertical = 11.dp, horizontal = 14.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                text = flightInfo.origin.iataCode,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                ),
                textAlign = TextAlign.Start,
                color = Black_alpha_60
            )

            Spacer(modifier = Modifier.weight(0.2f))
            DashedFlightView(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.weight(0.2f))
            Text(
                modifier = Modifier,
                text = flightInfo.destination.iataCode,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                ),
                textAlign = TextAlign.Start,
                color = Black_alpha_60
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier,
                text = "${flightInfo.origin.city} (${flightInfo.origin.country})",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                color = Color.Black
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                modifier = Modifier.padding(start = 8.dp),
                text = "${flightInfo.destination.city} (${flightInfo.destination.country})",
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.End,
                color = Color.Black
            )
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun DashedFlightView(modifier: Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxWidth()) {
            val pathEffect = PathEffect.dashPathEffect(floatArrayOf(5f, 5f))
            val imageWidth = 15.dp.toPx()
            val centerX = size.width / 2
            val extraPadding = 5.dp.toPx()
            val line1EndX = (centerX - imageWidth / 2) - extraPadding
            val line2StartX = (centerX + imageWidth / 2) + extraPadding
            drawLine(
                color = Orange_alpha_50,
                start = Offset(0f, 0f),
                end = Offset(line1EndX, 0f),
                pathEffect = pathEffect,
                strokeWidth = 2f
            )
            drawLine(
                color = Orange_alpha_50,
                start = Offset(line2StartX, 0f),
                end = Offset(size.width, 0f),
                pathEffect = pathEffect,
                strokeWidth = 2f
            )

        }

        Image(
            painter = painterResource(Res.drawable.ic_horizontal_flight),
            contentDescription = null
        )

    }

}
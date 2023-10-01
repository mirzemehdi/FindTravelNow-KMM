package ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.theme.Black_10
import ui.theme.Orange_55
import ui.theme.Red_48
import ui.theme.strings.Strings

@Composable
fun HomeScreen() {
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(horizontal = 30.dp)) {
        Spacer(modifier = Modifier.height(36.dp))
        TopTitleSection(modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(20.dp))
        CardViewBanner()
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun CardViewBanner(modifier: Modifier = Modifier) {
    val shape = RoundedCornerShape(9.dp)
    val gradient = Brush.verticalGradient(
        0f to Red_48,
        1f to Orange_55
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape)
            .background(
                brush = gradient, shape = shape
            )
            .padding(start = 16.dp)
    ) {
        Box(Modifier.align(Alignment.TopEnd).fillMaxWidth(0.5f)) {

            Image(
                painter = painterResource("drawable/ic_ellipse.xml"),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 4.dp, bottom = 11.dp),
                contentScale = ContentScale.Fit,

                )
            Image(
                painter = painterResource("drawable/img_travel_person.png"),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp, bottom = 19.dp),
                contentScale = ContentScale.Crop,
            )


        }

        Column(modifier = Modifier.fillMaxWidth(0.58f).padding(top = 30.dp)) {
            Text(
                text = Strings.home_screen_banner_text,
                style = MaterialTheme.typography.titleSmall,
                color = Color.White
            )
            val buttonShape = RoundedCornerShape(8.dp)
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clip(buttonShape)
                    .background(Black_10, buttonShape),
                shape = buttonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Black_10
                ),
                onClick = {}
            ) {
                Text(
                    text = Strings.btn_book_now,
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier=Modifier.height(32.dp))

        }

    }
}

@Composable
private fun TopTitleSection(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Text(
            modifier = Modifier.fillMaxWidth(0.7f),
            text = Strings.home_screen_title,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Start,
            color = Color.Black
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

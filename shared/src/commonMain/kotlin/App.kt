import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import theme.Black
import theme.MyApplicationTheme
import theme.Yellow_alpha_0
import theme.Yellow_alpha_39

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MyApplicationTheme {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0.7f to Yellow_alpha_0,
                        1.0f to Yellow_alpha_39,
                    )
                ).padding(40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(0.75f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {

                Image(
                    painter = painterResource("drawable/onboarding_1.xml"),
                    contentDescription = null,
                    modifier = Modifier.weight(1f, false).padding(vertical = 50.dp)
                )

                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = "Trusted and Free",
                    style = MaterialTheme.typography.displayMedium,
                    color = Black,
                )

                Text(
                    modifier = Modifier.padding(),
                    text = "We’re completely free to use – trusted by thousands people.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black,
                    textAlign = TextAlign.Center
                )

            }

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                modifier = Modifier.padding(bottom = 16.dp),
                onClick = {}
            ) {
                Text("SKIP", style = MaterialTheme.typography.bodyMedium)
            }


        }
    }
}

expect fun getPlatformName(): String
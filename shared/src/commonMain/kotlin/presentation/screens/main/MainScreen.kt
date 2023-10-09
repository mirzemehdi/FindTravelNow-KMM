package presentation.screens.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import presentation.screens.main.navigation.MainScreenDestination
import presentation.screens.main.navigation.MainScreenNavigation


@Composable
fun MainScreen() {
    MainScreenNavigation()
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier.fillMaxSize(),
    currentDestination: MainScreenDestination,
    onDestinationChanged: (MainScreenDestination) -> Unit,
    content: @Composable () -> Unit,
) {

    Column(modifier = modifier.fillMaxSize()) {
        Button(
            onClick = {
                onDestinationChanged(MainScreenDestination.Home)
            }
        ) {
            Text("Home")
        }

        Button(
            onClick = {
                onDestinationChanged(MainScreenDestination.More)
            }
        ) {
            Text("More")
        }
        content()
    }
}
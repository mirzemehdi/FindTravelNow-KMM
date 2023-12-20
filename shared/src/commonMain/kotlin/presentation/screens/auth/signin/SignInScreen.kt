package presentation.screens.auth.signin

import androidx.compose.material3.Text
import androidx.compose.material3.Button
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import util.auth.google.GoogleButtonUiContainer

@Composable
fun SignInScreen(modifier:Modifier=Modifier) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        GoogleButtonUiContainer(onGoogleSignInResult = {

        }){
            Button(
                onClick = { this.onClick() }
            ) {
                Text("SignIn with Google")
            }
        }
    }
}
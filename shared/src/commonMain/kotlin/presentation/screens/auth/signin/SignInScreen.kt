package presentation.screens.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import util.auth.google.GoogleAuthProvider
import util.auth.google.GoogleButtonUiContainer
import util.auth.google.GoogleUser

@Composable
fun SignInScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var signedInUser: GoogleUser? by remember { mutableStateOf(null) }

        val googleAuthProvider = koinInject<GoogleAuthProvider>()
        val coroutineScope = rememberCoroutineScope()

        if (signedInUser == null) {

            GoogleButtonUiContainer(onGoogleSignInResult = { googleUser ->
                coroutineScope.launch {
                    signedInUser=googleUser
                }

            }) {
                Button(
                    onClick = { this.onClick() }
                ) {
                    Text("SignIn with Google")
                }
            }
        } else {
            Text("User Name: ${signedInUser?.displayName}")
            Button(
                onClick = {
                    coroutineScope.launch {
                        googleAuthProvider.signOut()
                        signedInUser = null
                    }

                }
            ) {
                Text("Sign Out")
            }
        }
    }
}
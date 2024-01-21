package presentation.screens.auth.signin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mmk.kmpauth.firebase.apple.AppleButtonUiContainer
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.uihelper.apple.AppleSignInButton
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import dev.gitlive.firebase.auth.FirebaseUser

@Composable
fun SignInScreen(modifier: Modifier = Modifier) {
    Column(
        modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {

        var signedInUserName: String by remember { mutableStateOf("") }
        val onFirebaseResult: (Result<FirebaseUser?>) -> Unit = { result ->
            if (result.isSuccess) {
                val firebaseUser = result.getOrNull()
                signedInUserName =
                    firebaseUser?.displayName ?: firebaseUser?.email ?: "Null User"
            } else {
                signedInUserName = "Null User"
                println("Error Result: ${result.exceptionOrNull()?.message}")
            }

        }
        Text(
            text = signedInUserName,
            textAlign = TextAlign.Start,
        )


        AuthUiHelperButtonsAndFirebaseAuth(
            modifier = Modifier.width(IntrinsicSize.Max),
            onFirebaseResult = onFirebaseResult
        )



    }
}

@Composable
fun AuthUiHelperButtonsAndFirebaseAuth(
    modifier: Modifier = Modifier,
    onFirebaseResult: (Result<FirebaseUser?>) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        //Google Sign-In Button and authentication with Firebase
        GoogleButtonUiContainerFirebase(onResult = onFirebaseResult) {
            GoogleSignInButton(modifier = Modifier.fillMaxWidth()) { this.onClick() }
        }

        //Apple Sign-In Button and authentication with Firebase
        AppleButtonUiContainer(onResult = onFirebaseResult) {
            AppleSignInButton(modifier = Modifier.fillMaxWidth()) { this.onClick() }
        }

    }
}
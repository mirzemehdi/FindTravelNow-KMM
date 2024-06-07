package presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmk.kmpauth.core.KMPAuthInternalApi
import com.mmk.kmpauth.core.di.isAndroidPlatform
import com.mmk.kmpauth.firebase.apple.AppleButtonUiContainer
import com.mmk.kmpauth.firebase.github.GithubButtonUiContainer
import com.mmk.kmpauth.firebase.google.GoogleButtonUiContainerFirebase
import com.mmk.kmpauth.uihelper.apple.AppleSignInButton
import com.mmk.kmpauth.uihelper.google.GoogleButtonMode
import com.mmk.kmpauth.uihelper.google.GoogleSignInButton
import dev.gitlive.firebase.auth.FirebaseUser
import domain.model.AuthProvider
import findtravelnow_kmm.shared.generated.resources.Res
import findtravelnow_kmm.shared.generated.resources.ic_github
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.theme.strings.Strings

@Composable
fun AuthUiHelperButtonsAndFirebaseAuth(
    modifier: Modifier = Modifier,
    authProviders: List<AuthProvider> = AuthProvider.entries,
    onFirebaseResult: (Result<FirebaseUser?>) -> Unit,
) {
    val isExistOnlyOneAuthProvider by remember { mutableStateOf(authProviders.size == 1) }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        val shape = RoundedCornerShape(8.dp)
        val height = 56.dp
        val textFontSize = 24.sp

        if (authProviders.contains(AuthProvider.GOOGLE)) {
            //Google Sign-In Button and authentication with Firebase
            GoogleButtonUiContainerFirebase(onResult = onFirebaseResult) {
                LaunchedEffect(Unit) { if (isExistOnlyOneAuthProvider) this@GoogleButtonUiContainerFirebase.onClick() }
                GoogleSignInButton(
                    modifier = Modifier.fillMaxWidth().height(height),
                    text = Strings.btn_sign_in_with_google,
                    mode = GoogleButtonMode.Light,
                    fontSize = textFontSize,
                    shape = shape
                ) { this.onClick() }
            }
        }

        if (authProviders.contains(AuthProvider.APPLE)) {
            //Apple Sign-In Button and authentication with Firebase
            AppleButtonUiContainer(onResult = onFirebaseResult) {
                LaunchedEffect(Unit) { if (isExistOnlyOneAuthProvider) this@AppleButtonUiContainer.onClick() }
                AppleSignInButton(
                    modifier = Modifier.fillMaxWidth().height(height),
                    text = Strings.btn_sign_in_with_apple,
                    shape = shape
                ) { this.onClick() }
            }
        }

        if (authProviders.contains(AuthProvider.GITHUB)) {
            //Github Sign-In Button and authentication with Firebase
            GithubButtonUiContainer(onResult = onFirebaseResult) {
                LaunchedEffect(Unit) { if (isExistOnlyOneAuthProvider) this@GithubButtonUiContainer.onClick() }
                GithubSignInButton(
                    modifier = Modifier.fillMaxWidth().height(height),
                    text = Strings.btn_sign_in_with_github,
                    fontSize = textFontSize,
                    shape = shape
                ) { this.onClick() }
            }
        }

    }
}

@OptIn(ExperimentalResourceApi::class, KMPAuthInternalApi::class)
@Composable
private fun GithubSignInButton(
    modifier: Modifier = Modifier,
    text: String = "Sign in with Github",
    fontSize: TextUnit = 14.sp,
    shape: Shape = ButtonDefaults.shape,
    onClick: () -> Unit,
) {

    val horizontalPadding = if (isAndroidPlatform()) 12.dp else 16.dp
    val iconTextPadding = if (isAndroidPlatform()) 10.dp else 12.dp
    val containerColor = Color.White
    val contentColor = Color(0xFF1F1F1F)
    val buttonColor =
        ButtonDefaults.buttonColors(containerColor = containerColor, contentColor = contentColor)
    Button(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = horizontalPadding),
        onClick = onClick,
        shape = shape,
        colors = buttonColor,
        border = BorderStroke(
            width = 1.dp,
            color = Color(0xFF747775),
        ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(Res.drawable.ic_github),
                contentDescription = "githubicon"
            )
            Spacer(modifier = Modifier.width(iconTextPadding))
            Text(
                text = text,
                maxLines = 1,
                fontSize = fontSize,
            )
        }

    }


}
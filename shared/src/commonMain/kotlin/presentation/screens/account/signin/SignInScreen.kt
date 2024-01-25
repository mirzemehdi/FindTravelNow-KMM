package presentation.screens.account.signin

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
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
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.theme.Yellow_alpha_0
import presentation.theme.Yellow_alpha_39
import presentation.theme.strings.Strings
import util.logging.AppLogger

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onNavigatePrivacyPolicy: () -> Unit,
    onNavigateTermsConditions: () -> Unit,
) {
    val scrollState = rememberScrollState()
    LaunchedEffect(true) {
        scrollState.animateScrollTo(scrollState.maxValue, tween(1500))
    }
    Column(
        modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(
                Brush.verticalGradient(
                    0.8f to Yellow_alpha_0,
                    1.0f to Yellow_alpha_39,
                )
            )
            .padding(30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {


        Image(
            painter = painterResource("drawable/ic_logo.xml"),
            contentDescription = null,
            modifier = Modifier.padding(top = 4.dp).size(140.dp)
        )

        TitleText(modifier = Modifier.padding(top = 20.dp))

        AuthUiHelperButtonsAndFirebaseAuth(
            modifier = Modifier.padding(top = 32.dp).fillMaxWidth(),
            onFirebaseResult = { result ->
                result.onSuccess {
                    AppLogger.d("Successful sign in")
                }.onFailure {
                    AppLogger.e("Error occurred while signing in")
                }
            }
        )

        AgreePrivacyPolicyTermsConditionsText(
            modifier = Modifier.padding(top = 32.dp).fillMaxWidth(),
            onClickPrivacyPolicy = onNavigatePrivacyPolicy,
            onClickTermsService = onNavigateTermsConditions
        )


    }
}

@Composable
private fun AuthUiHelperButtonsAndFirebaseAuth(
    modifier: Modifier = Modifier,
    onFirebaseResult: (Result<FirebaseUser?>) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        val shape = RoundedCornerShape(8.dp)
        val height = 56.dp
        val textFontSize = 24.sp
        //Google Sign-In Button and authentication with Firebase
        GoogleButtonUiContainerFirebase(onResult = onFirebaseResult) {
            GoogleSignInButton(
                modifier = Modifier.fillMaxWidth().height(height),
                text = Strings.btn_sign_in_with_google,
                mode = GoogleButtonMode.Light,
                fontSize = textFontSize,
                shape = shape
            ) { this.onClick() }
        }

        //Apple Sign-In Button and authentication with Firebase
        AppleButtonUiContainer(onResult = onFirebaseResult) {
            AppleSignInButton(
                modifier = Modifier.fillMaxWidth().height(height),
                text = Strings.btn_sign_in_with_apple,
                shape = shape
            ) { this.onClick() }
        }

        //Github Sign-In Button and authentication with Firebase
        GithubButtonUiContainer(onResult = onFirebaseResult) {
            GithubSignInButton(
                modifier = Modifier.fillMaxWidth().height(height),
                text = Strings.btn_sign_in_with_github,
                fontSize = textFontSize,
                shape = shape
            ) { this.onClick() }
        }

    }
}


@Composable
private fun TitleText(modifier: Modifier) {
    val annotatedString = buildAnnotatedString {
        append(Strings.title_sign_in_account)
        appendLine()
        withStyle(
            style = SpanStyle(color = MaterialTheme.colorScheme.secondary)
        ) {
            append(Strings.your_travel_plans)
        }
    }
    Text(
        modifier = modifier,
        text = annotatedString,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.displayMedium.copy(color = Color.Black)
    )

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
                painter = painterResource("drawable/ic_github.xml"),
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

@Composable
private fun AgreePrivacyPolicyTermsConditionsText(
    modifier: Modifier,
    onClickPrivacyPolicy: () -> Unit,
    onClickTermsService: () -> Unit,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        val privacyPolicy = Strings.privacy_policy
        val termsConditions = Strings.terms_conditions
        val annotatedString = buildAnnotatedString {
            append(Strings.txt_agree_privacy_policy_and_terms)
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary,
                    textDecoration = TextDecoration.Underline
                ),
            ) {
                pushStringAnnotation(
                    tag = privacyPolicy,
                    annotation = privacyPolicy
                )
                append(privacyPolicy)
            }
            append(" ${Strings.and} ")
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.secondary,
                    textDecoration = TextDecoration.Underline
                ),
            ) {
                pushStringAnnotation(
                    tag = termsConditions,
                    annotation = termsConditions
                )// AnnotatedString.Builder
                append(termsConditions)
            }

        }
        ClickableText(
            text = annotatedString,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color.Black,
                fontWeight = FontWeight.Medium
            ),

            ) { offset ->
            annotatedString.getStringAnnotations(offset, offset)
                .firstOrNull()?.let { span ->
                    when (span.item) {
                        privacyPolicy -> onClickPrivacyPolicy()
                        termsConditions -> onClickTermsService()
                    }
                }
        }
    }
}
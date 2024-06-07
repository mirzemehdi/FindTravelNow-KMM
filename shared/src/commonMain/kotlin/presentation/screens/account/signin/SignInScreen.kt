package presentation.screens.account.signin

import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import findtravelnow_kmm.shared.generated.resources.Res
import findtravelnow_kmm.shared.generated.resources.ic_logo
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.components.AuthUiHelperButtonsAndFirebaseAuth
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
        scrollState.animateScrollTo(scrollState.maxValue, tween(500))
    }
    val systemBarPaddingValues = WindowInsets.systemBars.asPaddingValues()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(
            top = systemBarPaddingValues.calculateTopPadding(),
            bottom = 0.dp,
        ),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    start = 30.dp,
                    end = 30.dp,
                    top = systemBarPaddingValues.calculateTopPadding() + 30.dp,
                    bottom = 30.dp
                )
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {


            Image(
                painter = painterResource(Res.drawable.ic_logo),
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
                        coroutineScope.launch { snackbarHostState.showSnackbar(it.message?:"") }
                        AppLogger.e("Error occurred while signing in, $it")
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
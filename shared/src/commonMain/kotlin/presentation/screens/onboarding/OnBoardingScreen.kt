package presentation.screens.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.theme.Black_28
import presentation.theme.Black_34
import presentation.theme.Orange
import presentation.theme.Orange_51
import presentation.theme.Silver
import presentation.theme.Yellow_alpha_0
import presentation.theme.Yellow_alpha_39
import presentation.theme.strings.Strings
import util.asState


@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    uiStateHolder: OnBoardingUiStateHolder,
    onNavigateMain: () -> Unit,
    onClickTermsService: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
) {
    val uiState by uiStateHolder.uiState.asState()
    if (uiState.onBoardIsShown) onNavigateMain()
    OnBoardingScreen(
        modifier = modifier,
        uiState = uiState,
        onClickNavigateNext = uiStateHolder::onClickNavigateNext,
        onClickTermsService = onClickTermsService,
        onClickPrivacyPolicy = onClickPrivacyPolicy,
        onCheckPrivacyPolicy = uiStateHolder::onCheckPrivacyPolicy
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    uiState: OnBoardingScreenUiState = OnBoardingScreenUiState(),
    onClickNavigateNext: () -> Unit,
    onClickPrivacyPolicy: () -> Unit,
    onClickTermsService: () -> Unit,
    onCheckPrivacyPolicy: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    0.7f to Yellow_alpha_0,
                    1.0f to Yellow_alpha_39,
                )
            ).padding(top = 40.dp, bottom = 56.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val pagerState = rememberPagerState(
            initialPage = 0,
            initialPageOffsetFraction = 0f,
            pageCount = { uiState.pages.size }
        )
        HorizontalPager(state = pagerState, modifier = Modifier.fillMaxHeight(0.60f)) { pageIndex ->
            val onBoardingScreenData = uiState.pages[pageIndex]
            OnBoardingPager(
                onBoardingScreenData = onBoardingScreenData,
                modifier = Modifier.fillMaxSize().padding(horizontal = 40.dp)
            )
        }
        val coroutineScope = rememberCoroutineScope()
        HorizontalPagerIndicator(
            modifier = Modifier.padding(top = 30.dp),
            pagerState = pagerState,
            onClickIndicator = { index ->
                coroutineScope.launch { pagerState.animateScrollToPage(index) }
            }

        )

        Spacer(modifier = Modifier.weight(1f))

        Column(
            modifier = Modifier
                .padding(start = 40.dp, end = 40.dp)
                .weight(1f),

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val isLastPage = pagerState.currentPage == (pagerState.pageCount - 1)
            AcceptPrivacyPolicyTermsConditionsText(
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                isChecked = uiState.isPrivacyPolicyChecked,
                onClickPrivacyPolicy = onClickPrivacyPolicy,
                onClickTermsService = onClickTermsService,
                onCheckPrivacyPolicy = onCheckPrivacyPolicy,
            )
            AnimatedVisibility(
                visible = isLastPage,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                GetStartedButton(modifier = Modifier.fillMaxWidth(), onClick = onClickNavigateNext)
            }
            AnimatedVisibility(
                visible = isLastPage.not(),
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                SkipButton(onClick = onClickNavigateNext)
            }
        }


    }
}

@Composable
fun AcceptPrivacyPolicyTermsConditionsText(
    modifier: Modifier,
    isChecked:Boolean,
    onClickPrivacyPolicy: () -> Unit,
    onClickTermsService: () -> Unit,
    onCheckPrivacyPolicy: () -> Unit,
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { onCheckPrivacyPolicy() },
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.secondary,
                checkmarkColor = MaterialTheme.colorScheme.onSecondary
            )
        )
        Spacer(modifier = Modifier.width(4.dp))
        val privacyPolicy = Strings.privacy_policy
        val termsConditions = Strings.terms_conditions
        val annotatedString = buildAnnotatedString {
            append(Strings.txt_accept_privacy_policy_and_terms)
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
            style = MaterialTheme.typography.bodySmall.copy(color = Black_28),

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

@Composable
private fun GetStartedButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val shape = RoundedCornerShape(8.dp)
    Button(
        modifier = modifier
            .height(64.dp)
            .clip(shape)
            .background(
                brush = Brush.linearGradient(
                    0f to Orange,
                    1f to Orange_51
                )
            ),
        shape = shape,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        onClick = { onClick() },
    ) {
        Text(
            text = Strings.btn_get_started,
            color = Color.White,
            style = MaterialTheme.typography.titleSmall
        )
    }

}

@Composable
private fun SkipButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    TextButton(
        modifier = modifier,
        onClick = { onClick() }
    ) {
        Text(
            Strings.btn_skip,
            style = MaterialTheme.typography.bodyMedium,
            color = Black_34,
            modifier = Modifier.padding(horizontal = 4.dp)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
private fun OnBoardingPager(
    modifier: Modifier = Modifier,
    onBoardingScreenData: OnBoardingScreenData,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            painter = painterResource(onBoardingScreenData.imageRes),
            contentDescription = null,
            modifier = Modifier
                .fillMaxHeight(0.6f)
                .fillMaxWidth(0.8f)
        )

        Text(
            modifier = Modifier.padding(top = 50.dp),
            text = onBoardingScreenData.title,
            style = MaterialTheme.typography.displayMedium,
            color = Black_28,
            textAlign = TextAlign.Center
        )

        Text(
            modifier = Modifier.padding(top = 20.dp),
            text = onBoardingScreenData.description,
            style = MaterialTheme.typography.bodyMedium,
            color = Black_28,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun HorizontalPagerIndicator(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onClickIndicator: (Int) -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.secondary else Silver
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(13.dp)
                    .clip(CircleShape)
                    .background(color = color, shape = CircleShape)
                    .clickable { onClickIndicator(iteration) }

            )
        }
    }
}
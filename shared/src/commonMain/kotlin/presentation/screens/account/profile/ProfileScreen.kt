package presentation.screens.account.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.mmk.kmprevenuecat.purchases.data.CustomerInfo
import com.mmk.kmprevenuecat.purchases.ui.Paywall
import com.mmk.kmprevenuecat.purchases.ui.PaywallListener
import dev.gitlive.firebase.auth.FirebaseUser
import domain.model.AuthProvider
import domain.model.User
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import presentation.components.AuthUiHelperButtonsAndFirebaseAuth
import presentation.components.DeleteUserConfirmationDialog
import presentation.components.ExpandableBoxItem
import presentation.components.GradientButton
import presentation.components.MyAppCircularProgressIndicator
import presentation.components.MyAppLabelledTextInput
import presentation.components.SubscriptionPaywall
import presentation.components.TitleDescription
import presentation.theme.Alabaster
import presentation.theme.Black_22
import presentation.theme.strings.Strings
import util.asState
import util.isAndroid


@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileScreen(modifier: Modifier = Modifier, uiStateHolder: ProfileUiStateHolder) {
    val uiState by uiStateHolder.profileScreenUiState.asState()
    if (uiState.reAuthenticateUserViewShown) {
        SocialLoginsBottomSheet(
            isLoading = uiState.isDeleteInProgress,
            authProviders = uiState.currentUserAuthProviderList,
            onDismiss = uiStateHolder::onDismissReAuthenticateView,
            onResult = uiStateHolder::onUserReAuthenticatedResult
        )
    }

    if (uiState.deleteUserDialogShown) {
        DeleteUserConfirmationDialog(
            onConfirm = uiStateHolder::onConfirmDeleteAccount,
            onDismiss = uiStateHolder::onDismissDeleteUserConfirmationDialog
        )
    }

    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        LaunchedEffect(uiState.message) {
            if (uiState.message.isNullOrEmpty().not()) {
                snackbarHostState.showSnackbar(uiState.message ?: "")
                uiStateHolder.onMessageIsShown()
            }
        }
        uiState.currentUser?.let { currentUser ->
            ProfileScreen(
                modifier = Modifier.fillMaxSize(),
                currentUser = currentUser,
                isEditMode = uiState.isEditMode,
                isEditInProgress = uiState.isEditInProgress,
                onClickLogOut = uiStateHolder::onClickLogOut,
                onClickDeleteAccount = uiStateHolder::onClickDeleteAccount,
                onClickEnableEditMode = uiStateHolder::onCLickEnableEditMode,
                onClickUpdateProfile = uiStateHolder::onClickUpdateProfile,
                onChangeDisplayName = uiStateHolder::onChangeDisplayName,
                onClickUpgradePremium = uiStateHolder::onClickUpgradePremium,
            )
        }

    }


    if (uiState.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            MyAppCircularProgressIndicator()
        }
    }
    if (uiState.isSubscribePlansViewVisible) {
        SubscriptionPaywall(
            onDismiss = uiStateHolder::onDismissSubscriptionPlansView,
            listener = object:PaywallListener{
                override fun onPurchaseCompleted(customerInfo: CustomerInfo?) {
                    super.onPurchaseCompleted(customerInfo)
                    uiStateHolder.onSubscriptionPurchaseCompleted()
                }

                override fun onPurchaseError(error: String?) {
                    super.onPurchaseError(error)
                    uiStateHolder.onSubscriptionPurchaseError(error)
                }


                override fun onRestoreCompleted(customerInfo: CustomerInfo?) {
                    super.onRestoreCompleted(customerInfo)
                    uiStateHolder.onSubscriptionPurchaseCompleted()
                }

                override fun onRestoreError(error: String?) {
                    super.onRestoreError(error)
                    uiStateHolder.onSubscriptionPurchaseError(error)
                }
            }
        )
    }
}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ProfileScreen(
    modifier: Modifier = Modifier,
    currentUser: User,
    isEditMode: Boolean,
    isEditInProgress: Boolean,
    onClickLogOut: () -> Unit,
    onClickDeleteAccount: () -> Unit,
    onClickEnableEditMode: () -> Unit,
    onClickUpdateProfile: () -> Unit,
    onChangeDisplayName: (String) -> Unit,
    onClickUpgradePremium: () -> Unit,
) {

    Box(modifier = modifier) {
        val imageSize = 108
        val extraTopMargin = 24
        val topContainerHeight = imageSize / 2 + extraTopMargin
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topContainerHeight.dp)
                .background(MaterialTheme.colorScheme.primary)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .padding(top = extraTopMargin.dp)
                    .size(imageSize.dp)
            ) {
                var isImageLoadedSuccessfully by remember { mutableStateOf(false) }
                AsyncImage(
                    model = currentUser.profilePicSrc,
                    contentDescription = null,
                    onState = {
                        isImageLoadedSuccessfully = it is AsyncImagePainter.State.Success
                    },
                    modifier = Modifier
                        .size(imageSize.dp)
                        .clip(CircleShape)
                )
                if (isImageLoadedSuccessfully.not())
                    Image(
                        painter = painterResource("drawable/ic_person_placeholder.xml"),
                        contentDescription = null,
                        modifier = Modifier
                            .size(imageSize.dp)
                            .clip(CircleShape)
                            .background(Color.White, shape = CircleShape)
                    )
            }




            Text(
                modifier = Modifier.padding(top = 12.dp),
                text = currentUser.displayName,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 19.sp,
                    color = Black_22
                )
            )

            if (isEditMode.not()) {
                Text(
                    modifier = Modifier
                        .clip(ButtonDefaults.textShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(color = MaterialTheme.colorScheme.primary),
                            onClick = { onClickEnableEditMode() }
                        )
                        .padding(horizontal = 16.dp, vertical = 4.dp),
                    text = Strings.edit_profile,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.secondary
                    )
                )

                BasicInfo(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    currentUser = currentUser
                )

                SubscriptionInfo(
                    modifier = Modifier.fillMaxWidth().padding(top = 20.dp),
                    currentUser = currentUser,
                    onClickUpgradePremium = onClickUpgradePremium
                )
                GradientButton(
                    modifier = Modifier.padding(top = 32.dp).fillMaxWidth(),
                    text = Strings.btn_log_out,
                    onClick = { onClickLogOut() }
                )

                TextButton(
                    onClick = { onClickDeleteAccount() },
                    modifier = Modifier.padding(top = 24.dp)
                ) {
                    Text(
                        text = Strings.btn_delete_account,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Start,
                    )
                }
            } else {
                EditInfoContainer(
                    modifier = Modifier.fillMaxWidth().padding(top = 24.dp),
                    currentUser = currentUser,
                    onChangeDisplayName = onChangeDisplayName
                )

                GradientButton(
                    modifier = Modifier.padding(top = 16.dp).fillMaxWidth(),
                    text = Strings.btn_update_profile,
                    isLoading = isEditInProgress,
                    onClick = { onClickUpdateProfile() }
                )
            }


        }

    }


}

@Composable
private fun BasicInfo(modifier: Modifier = Modifier, currentUser: User) {
    var isExpanded by remember { mutableStateOf(false) }
    ExpandableBoxItem(modifier = modifier,
        text = Strings.basic_info,
        isExpanded = isExpanded,
        onToggle = { isExpanded = !isExpanded }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            TitleDescription(
                title = Strings.display_name_title,
                description = currentUser.displayName,
            )
            currentUser.email?.let { email ->
                TitleDescription(
                    title = Strings.email_address_title,
                    description = email,
                )
            }

        }
    }
}

@Composable
private fun SubscriptionInfo(
    modifier: Modifier = Modifier,
    currentUser: User,
    onClickUpgradePremium: () -> Unit
) {
    var isExpanded by remember { mutableStateOf(false) }
    ExpandableBoxItem(modifier = modifier,
        text = Strings.subscription_info,
        isExpanded = isExpanded,
        onToggle = { isExpanded = !isExpanded }
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {


            TitleDescription(
                title = Strings.subscription_plan_title,
                description = if (currentUser.hasPremiumSubscription()) Strings.subscription_plan_premium else Strings.subscription_plan_free,
            )

            if (
                currentUser.hasPremiumSubscription() &&
                (currentUser.subscription?.expirationDate ?: 0L) == 0L
            )
                TitleDescription(
                    title = Strings.subscription_plan_expiration_date,
                    description = Strings.subscription_lifetime,
                )
            if (currentUser.hasPremiumSubscription().not()) {
                TextButton(
                    onClick = { onClickUpgradePremium() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = Strings.btn_get_premium_subscription_plan_free,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Start,
                    )
                }
            }

        }
    }
}

@Composable
private fun EditInfoContainer(
    modifier: Modifier = Modifier,
    currentUser: User,
    onChangeDisplayName: (String) -> Unit,
) {

    val shape = RoundedCornerShape(10.dp)
    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Alabaster,
                shape = shape
            )
            .background(color = Color.White, shape = shape)
            .clip(shape)
            .padding(start = 19.dp, top = 20.dp, bottom = 24.dp, end = 19.dp),
        contentAlignment = Alignment.CenterStart

    ) {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            MyAppLabelledTextInput(
                title = Strings.display_name_title,
                inputText = currentUser.displayName,
                onValueChange = onChangeDisplayName

            )
            currentUser.email?.let { email ->
                MyAppLabelledTextInput(
                    title = Strings.email_address_title,
                    enabled = false,
                    inputText = email,
                    onValueChange = {}
                )
            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SocialLoginsBottomSheet(
    isLoading: Boolean = false,
    authProviders: List<AuthProvider>,
    onDismiss: () -> Unit,
    onResult: (Result<FirebaseUser?>) -> Unit,
) {
    ModalBottomSheet(

        windowInsets = WindowInsets(0),
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        dragHandle = {},
        onDismissRequest = { onDismiss() }
    ) {
        Box(modifier = Modifier.padding(40.dp)) {
            AuthUiHelperButtonsAndFirebaseAuth(
                onFirebaseResult = onResult,
                authProviders = authProviders
            )
            if (isLoading) MyAppCircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }


}

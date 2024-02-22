package presentation.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mmk.kmprevenuecat.purchases.ui.Paywall
import com.mmk.kmprevenuecat.purchases.ui.PaywallListener
import util.isAndroid

@Composable
fun SubscriptionPaywall(onDismiss: () -> Unit, listener: PaywallListener? = null) {
    //In ios no need for fullscreen dialog,in android we add extra full screen dialog
    if (isAndroid())
        Dialog(
            properties = DialogProperties(usePlatformDefaultWidth = false),
            onDismissRequest = onDismiss
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                Paywall(
                    shouldDisplayDismissButton = true,
                    onDismiss = onDismiss,
                    listener = listener
                )
            }
        }
    else {
        Paywall(
            shouldDisplayDismissButton = true,
            onDismiss = onDismiss,
            listener = listener
        )
    }
}
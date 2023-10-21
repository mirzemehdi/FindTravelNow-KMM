package presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import presentation.theme.Black_22
import presentation.theme.Black_34
import presentation.theme.Orange
import presentation.theme.Orange_51
import presentation.theme.strings.Strings
import util.AppOpenerUtil

@Composable
fun AppUpgradeRequiredDialog(modifier: Modifier = Modifier) {
    val appOpenerUtil = koinInject<AppOpenerUtil>()
    AlertDialog(
        modifier=modifier,
        onDismissRequest = { },
        icon = { Icon(imageVector = Icons.Filled.Info, contentDescription = "Info") },
        title = {
            Text(text = Strings.title_app_upgrade_dialog, color = Black_22)
        },
        text = {
            Text(
                text = Strings.description_app_upgrade_dialog,
                color = Black_22
            )
        },
        confirmButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                onClick = { appOpenerUtil.openStorePage() },
            ) {
                Text(
                    text = Strings.btn_update,
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall
                )
            }

        }
    )

}
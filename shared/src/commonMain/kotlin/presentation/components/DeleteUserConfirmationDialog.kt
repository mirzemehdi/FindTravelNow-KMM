package presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import presentation.theme.Black_22
import presentation.theme.strings.Strings

@Composable
fun DeleteUserConfirmationDialog(
    modifier: Modifier = Modifier,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        modifier = modifier,
        onDismissRequest = {onDismiss() },
        icon = { Icon(imageVector = Icons.Filled.Delete, contentDescription = "Delete") },
        title = {
            Text(text = Strings.title_delete_user_dialog, color = Black_22)
        },
        text = {
            Text(
                text = Strings.description_delete_user_dialog,
                color = Black_22
            )
        },
        confirmButton = {
            TextButton(
                onClick = { onConfirm() },
            ) {
                Text(
                    text = Strings.btn_delete,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
            }

        },
        dismissButton = {
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                onClick = { onDismiss() },
            ) {
                Text(
                    text = Strings.btn_cancel,
                    color = Color.White,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    )

}
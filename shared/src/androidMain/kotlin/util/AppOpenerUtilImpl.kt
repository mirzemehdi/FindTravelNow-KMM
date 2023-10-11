package util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import presentation.theme.strings.Strings

class AppOpenerUtilImpl(private val context: Context) : AppOpenerUtil {
    override fun openStorePage() {
        val appId = context.packageName
        println("AppOpenerUtilImpl: $appId")
        val uri = Uri.parse("market://details?id=$appId")
        val intentFlags = Intent.FLAG_ACTIVITY_NO_HISTORY or
                Intent.FLAG_ACTIVITY_NEW_TASK
        val intent = Intent(Intent.ACTION_VIEW, uri).apply {
            addFlags(intentFlags)
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val webUri = Uri.parse(getPlayStoreLink())
            val webIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
                addFlags(intentFlags)
            }
            context.startActivity(webIntent)
        }
    }

    override fun shareApp() {
        val flag = Intent.FLAG_ACTIVITY_NEW_TASK
        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/plain"
            putExtra(Intent.EXTRA_SUBJECT, Strings.app_name)
            putExtra(
                Intent.EXTRA_TEXT, "${Strings.msg_share_app}\n" +
                        "\n${getPlayStoreLink()}"
            )
            flags = flag
        }

        context.startActivity(Intent.createChooser(shareIntent, "Choose one").apply {
            flags = flag
        })
    }

    override fun openFeedbackMail() {
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:${Strings.contact_email_address}")
            setPackage("com.google.android.gm")
            putExtra(Intent.EXTRA_SUBJECT, Strings.app_name)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        context.startActivity(intent)
    }

    private fun getPlayStoreLink(): String {
        val appId = context.packageName
        return "https://play.google.com/store/apps/details?id=$appId"
    }
}
package util

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri

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
            val webUri = Uri.parse("http://play.google.com/store/apps/details?id=$appId")
            val webIntent = Intent(Intent.ACTION_VIEW, webUri).apply {
                addFlags(intentFlags)
            }
            context.startActivity(webIntent)
        }
    }
}
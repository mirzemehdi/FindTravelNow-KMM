package util

import platform.Foundation.NSURL
import platform.MessageUI.MFMailComposeViewController
import platform.UIKit.UIActivityViewController
import platform.UIKit.UIApplication
import presentation.theme.strings.Strings

class AppOpenerUtilImpl : AppOpenerUtil {
    override fun openStorePage() {
        val appId = "" //TODO Update app ID
        val url = NSURL(string = "itms-apps://apps.apple.com/app/id$appId")
        if (UIApplication.sharedApplication.canOpenURL(url)) {
            UIApplication.sharedApplication.openURL(url)
        } else {
            val appStoreLink = "https://findtravelnow.com"
            val appLinkUrl = NSURL(string = appStoreLink)
            UIApplication.sharedApplication.openURL(appLinkUrl)
        }
    }

    override fun shareApp() {
        val shareMessage = "${Strings.msg_share_app}\n\n"
        val items = listOf("$shareMessage ${getAppStoreLink()}")
        val activityViewController = UIActivityViewController(items, null)
        val uiController = UIApplication.sharedApplication.keyWindow?.rootViewController
        uiController?.let { it.presentViewController(activityViewController, true, {}) }
    }

    override fun openFeedbackMail() {
        try {
            val mailComposeViewController = MFMailComposeViewController()
            mailComposeViewController.setSubject(Strings.app_name)
            mailComposeViewController.setToRecipients(listOf(Strings.contact_email_address))
            val rootViewController = UIApplication.sharedApplication.keyWindow?.rootViewController
            rootViewController?.presentViewController(mailComposeViewController, true, {})
        } catch (e: Exception) {
            val mailUrl = NSURL(string = "mailto:${Strings.contact_email_address}")
            UIApplication.sharedApplication.openURL(mailUrl)
        }

    }

    private fun getAppStoreLink(): String {
        val appId = "" //TODO Update app ID
        return "https://apps.apple.com/app/$appId"
    }
}
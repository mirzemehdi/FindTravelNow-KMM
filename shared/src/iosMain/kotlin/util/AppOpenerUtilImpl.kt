package util

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

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
}
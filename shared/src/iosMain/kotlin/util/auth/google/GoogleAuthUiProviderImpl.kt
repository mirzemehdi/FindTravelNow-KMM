package util.auth.google

import cocoapods.GoogleSignIn.GIDSignIn
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIApplication
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

internal class GoogleAuthUiProviderImpl : GoogleAuthUiProvider {
    @OptIn(ExperimentalForeignApi::class)
    override suspend fun signIn(): String? = suspendCoroutine { continutation ->

        val rootViewController =
            UIApplication.sharedApplication.keyWindow?.rootViewController

        if (rootViewController == null) continutation.resume(null)
        else {
            GIDSignIn.sharedInstance
                .signInWithPresentingViewController(rootViewController) { gidSignInResult, nsError ->
                    nsError?.let { println("Error While signing: $nsError") }
                    if (gidSignInResult != null) continutation.resume(gidSignInResult.user.idToken?.tokenString)
                    else continutation.resume(null)
                }

        }
    }


}
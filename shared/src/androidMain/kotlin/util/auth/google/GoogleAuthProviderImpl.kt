package util.auth.google

import androidx.compose.runtime.Composable

internal class GoogleAuthProviderImpl(private val uiProvider: GoogleAuthUiProvider) :
    GoogleAuthProvider {

    @Composable
    override fun getUiProvider(): GoogleAuthUiProvider = uiProvider

    override suspend fun signOut() {

    }
}
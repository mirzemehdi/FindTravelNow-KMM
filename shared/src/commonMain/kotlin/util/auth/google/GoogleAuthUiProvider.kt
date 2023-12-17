package util.auth.google

interface GoogleAuthUiProvider {

    /**
     * Opens Sign In with Google UI,
     * @return returns googleIdToken
     */
    suspend fun signIn(): String?
}
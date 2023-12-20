package util.auth.google

data class GoogleUser(
    val idToken: String,
    val displayName: String = "",
    val profilePicUrl: String? = null,
)
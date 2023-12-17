package util.auth.google

/**
 * android clientId is the same as serverId
 */
data class GoogleAuthCredentials(
    val serverId: String,
    val iosClientId: String,
)

package domain.model

data class User(
    val id: String = "",
    val displayName: String = "",
    val profilePicSrc: String? = "",
    val email: String? = null,
)
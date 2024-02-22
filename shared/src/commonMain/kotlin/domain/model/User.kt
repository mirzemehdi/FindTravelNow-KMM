package domain.model

data class User(
    val id: String = "",
    val displayName: String = "",
    val profilePicSrc: String? = "",
    val email: String? = null,
    val subscription: Subscription? = null
) {
    fun hasPremiumSubscription() = subscription != null && subscription.isActive
}
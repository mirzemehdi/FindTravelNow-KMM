package domain.model

data class Subscription(
    val entitlementId:String,
    val expirationDate:Long?,
    val isActive:Boolean
)

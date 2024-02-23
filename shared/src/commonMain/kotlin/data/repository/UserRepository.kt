package data.repository

import com.mmk.kmprevenuecat.purchases.CacheFetchPolicy
import com.mmk.kmprevenuecat.purchases.Purchases
import com.mmk.kmprevenuecat.purchases.PurchasesException
import com.mmk.kmprevenuecat.purchases.awaitCustomerInfo
import com.mmk.kmprevenuecat.purchases.data.CustomerInfo
import data.source.remote.apiservice.UserApiService
import data.source.remote.request.UserUpdateRequest
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.model.AuthProvider
import domain.model.Subscription
import domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import util.logging.AppLogger
import kotlin.coroutines.CoroutineContext

class UserRepository(
    private val userApiService: UserApiService,
    private val backgroundScope: CoroutineContext = Dispatchers.IO
) {

    private val currentUserSubscriptionFlow = MutableStateFlow<Subscription?>(null)
    private val firebaseCurrentUserFlow = Firebase.auth.authStateChanged
        .onEach { firebaseUser ->
            firebaseUser?.let {
                Purchases.login(firebaseUser.uid) { subscriptionLoginResult ->
                    val subscription =
                        subscriptionLoginResult.getOrNull()?.customerInfo?.asSubscription()
                    currentUserSubscriptionFlow.tryEmit(subscription)
                }
            }
        }
        .map { firebaseUser ->
            if (firebaseUser == null) null
            else userApiService.createOrGetUser().data?.mapToDomainModel().also {
                Purchases.setAttributes(mapOf("\$email" to it?.email,"\$displayName" to it?.displayName))
            }
        }


    val currentUser =
        combine(firebaseCurrentUserFlow, currentUserSubscriptionFlow) { user, subscription ->
            user?.copy(subscription = subscription)
        }
            .flowOn(backgroundScope)
            .stateIn(MainScope(), SharingStarted.WhileSubscribed(5000), null)


    fun refreshUserSubscription(){
        MainScope().launch {
            currentUserSubscriptionFlow.emit(getUserSubscription())
        }
    }
    private suspend fun getUserSubscription(): Subscription? {
        AppLogger.d("Get User Subscription is called")
        val customerInfo = try {
            Purchases.awaitCustomerInfo(CacheFetchPolicy.FETCH_CURRENT)
        } catch (e: PurchasesException) {
            AppLogger.e("Get User Subscription error: $e")
            null
        } ?: return null
        return customerInfo.asSubscription()
    }

    private fun CustomerInfo?.asSubscription(): Subscription? {
        val customerInfo = this ?: return null
        val premiumSubscriptionEntitlement = customerInfo.entitlements.all["Premium"]
        return premiumSubscriptionEntitlement?.let {
            Subscription(
                entitlementId = it.identifier,
                expirationDate = it.expirationDate,
                isActive = it.isActive
            )
        }
    }

    suspend fun logOut() = withContext(backgroundScope) {
        Firebase.auth.signOut()
        Purchases.logOut { }
    }

    suspend fun deleteAccount() = withContext(backgroundScope) {
        val currentUser = Firebase.auth.currentUser
        currentUser?.delete()
        kotlin.runCatching { logOut() }
    }

    suspend fun updateProfile(user: User) = withContext(backgroundScope) {
        userApiService.updateUser(
            userUpdateRequest = UserUpdateRequest(
                displayName = user.displayName,
                profilePicUrl = user.profilePicSrc
            )
        )
    }

    fun getCurrentUserProviders(): List<AuthProvider> {
        val currentUser = Firebase.auth.currentUser
        return currentUser?.providerData?.mapNotNull {
            val providerId = it.providerId
            AppLogger.d("ProviderId: $providerId")
            when (providerId) {
                "google.com" -> AuthProvider.GOOGLE
                "apple.com" -> AuthProvider.APPLE
                "github.com" -> AuthProvider.GITHUB
                else -> null
            }
        } ?: emptyList()
    }

}
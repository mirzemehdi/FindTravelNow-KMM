package data.repository

import data.source.remote.apiservice.UserApiService
import data.source.remote.request.UserUpdateRequest
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.model.AuthProvider
import domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import util.logging.AppLogger
import kotlin.coroutines.CoroutineContext

class UserRepository(
    private val userApiService: UserApiService,
    private val backgroundScope: CoroutineContext = Dispatchers.IO
) {


    val currentUser = Firebase.auth.authStateChanged.map {
        if (it == null) null
        else userApiService.createOrGetUser().data?.mapToDomainModel()
    }.flowOn(backgroundScope).stateIn(MainScope(), SharingStarted.Eagerly,null)

    suspend fun logOut() = withContext(backgroundScope) {
        Firebase.auth.signOut()
    }

    suspend fun deleteAccount() = withContext(backgroundScope) {
        val currentUser = Firebase.auth.currentUser
        currentUser?.delete()
        kotlin.runCatching { Firebase.auth.signOut() }
    }

    suspend fun updateProfile(user: User) = withContext(backgroundScope) {
        userApiService.updateUser(userUpdateRequest = UserUpdateRequest(displayName = user.displayName,profilePicUrl = user.profilePicSrc))
//        val currentUser = Firebase.auth.currentUser
//        currentUser?.updateProfile(displayName = user.displayName, photoUrl = user.profilePicSrc)
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
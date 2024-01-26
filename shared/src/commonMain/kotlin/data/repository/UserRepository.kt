package data.repository

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth
import domain.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import util.logging.AppLogger
import kotlin.coroutines.CoroutineContext

class UserRepository(private val backgroundScope: CoroutineContext = Dispatchers.IO) {


    val currentUser = Firebase.auth.authStateChanged.map {
        if (it == null) null else User(
            id = it.uid,
            displayName = it.displayName ?: "",
            profilePicSrc = it.photoURL,
            email = it.email
        )
    }

    suspend fun logOut() = withContext(backgroundScope) {
        Firebase.auth.signOut()
    }

    suspend fun deleteAccount() = withContext(backgroundScope) {
        val currentUser=Firebase.auth.currentUser
       currentUser?.providerData?.forEach {
            AppLogger.e("ProviderId: ${it.providerId}")

        }
        currentUser?.delete()
    }

}
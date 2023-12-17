package di

import androidx.credentials.CredentialManager
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module
import util.AndroidAppVersion
import util.AppOpenerUtil
import util.AppOpenerUtilImpl
import util.AppVersion
import util.auth.google.GoogleAuthProvider
import util.auth.google.GoogleAuthProviderImpl
import util.auth.google.GoogleAuthUiProvider
import util.auth.google.GoogleAuthUiProviderImpl

private val googleAuthModule = module {
    factory { CredentialManager.create(androidContext()) } bind CredentialManager::class
    factoryOf(::GoogleAuthProviderImpl) bind GoogleAuthProvider::class
}

internal actual val platformModule: Module = module {
    factoryOf(::AppOpenerUtilImpl) bind AppOpenerUtil::class
    factoryOf(::AndroidAppVersion) bind AppVersion::class
    includes(googleAuthModule)
}
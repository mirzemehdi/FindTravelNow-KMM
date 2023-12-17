package di

import data.di.dataModule
import domain.di.domainModule
import org.koin.core.module.Module
import org.koin.dsl.module
import presentation.di.presentationModule
import secrets.BuildConfig
import util.auth.google.GoogleAuthCredentials


internal expect val platformModule: Module

private val utilModule = module {
    factory {
        GoogleAuthCredentials(
            serverId = BuildConfig.GOOGLE_AUTH_SERVER_ID,
            iosClientId = ""
        )
    }
}
val appModules: List<Module>
    get() =
        platformModule + domainModule + dataModule + presentationModule + utilModule

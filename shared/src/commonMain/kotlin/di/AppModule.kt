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
            serverId = "400988245981-u6ajdq65cv1utc6b0j7mtnhc5ap54kbd.apps.googleusercontent.com",
        )
    }
}
val appModules: List<Module>
    get() =
        platformModule + domainModule + dataModule + presentationModule + utilModule

package data

import data.source.preferences.UserPreferences
import data.source.preferences.UserPreferencesImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    singleOf(::UserPreferencesImpl) bind UserPreferences::class
}
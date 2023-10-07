package data

import com.russhwolf.settings.Settings
import data.source.preferences.UserPreferences
import data.source.preferences.UserPreferencesImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { Settings() } bind Settings::class
    singleOf(::UserPreferencesImpl) bind UserPreferences::class
}
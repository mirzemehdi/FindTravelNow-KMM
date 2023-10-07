package data.source.preferences

import kotlinx.coroutines.delay

internal class UserPreferencesImpl :UserPreferences {

    override suspend fun getString(key: String, defaultValue: String?): String? {
        TODO("Not yet implemented")
    }

    override suspend fun getInt(key: String, defaultValue: Int?): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        delay(2000)
        return true
    }

    override suspend fun putString(key: String, value: String) {
        TODO("Not yet implemented")
    }

    override suspend fun putInt(key: String, value: Int) {
        TODO("Not yet implemented")
    }

    override suspend fun putBoolean(key: String, value: Boolean) {

    }
}
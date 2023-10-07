package data.source.preferences

import com.russhwolf.settings.Settings

internal class UserPreferencesImpl(private val multiplatformSettings: Settings) : UserPreferences {

    override suspend fun getString(key: String, defaultValue: String?): String? {
        return multiplatformSettings.getStringOrNull(key)?.let { defaultValue }
    }

    override suspend fun getInt(key: String, defaultValue: Int?): Int? {
        return multiplatformSettings.getIntOrNull(key)?.let { defaultValue }

    }

    override suspend fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return multiplatformSettings.getBoolean(key, defaultValue)

    }

    override suspend fun putString(key: String, value: String) {
        multiplatformSettings.putString(key, value)
    }

    override suspend fun putInt(key: String, value: Int) {
        multiplatformSettings.putInt(key, value)
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        multiplatformSettings.putBoolean(key, value)
    }

    override suspend fun clear() {
        multiplatformSettings.clear()
    }
}
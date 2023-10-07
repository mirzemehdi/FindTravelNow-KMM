package data.source.preferences


interface UserPreferences {

    companion object Keys {
        const val KEY_IS_ONBOARD_SHOWN = "KEY_IS_ONBOARD_SHOWN"
    }

    suspend fun getString(key: String, defaultValue: String? = null): String?
    suspend fun getInt(key: String, defaultValue: Int? = null): Int?
    suspend fun getBoolean(key: String, defaultValue: Boolean = false): Boolean

    suspend fun putString(key: String, value: String)
    suspend fun putInt(key: String, value: Int)
    suspend fun putBoolean(key: String, value: Boolean)
}
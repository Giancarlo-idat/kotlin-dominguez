@file:Suppress("DEPRECATION")

package com.store.importacionesdominguez.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object SharedPreferences {

    private const val SHARED_PREF_NAME = "com.store.importacionesdominguez"
    private const val KEY_AUTHENTICATED = "authenticated"

    // Shared Preferences para guardar el token
    private const val SHARED_NAME: String = "login"
    private const val TOKEN_KEY = "token"
    private const val KEY_USER_EMAIL = "user_email"

    fun defaultPrefs(context: Context): SharedPreferences
            = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPrefs(context: Context, name: String): SharedPreferences
            = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.edit(operation: (SharedPreferences.Editor) -> Unit) {
        val editor = this.edit()
        operation(editor)
        editor.apply()
    }

    operator fun SharedPreferences.set(key: String, value: Any?)
            = when (value) {
        is String? -> edit { it.putString(key, value) }
        is Int -> edit { it.putInt(key, value) }
        is Boolean -> edit { it.putBoolean(key, value) }
        is Float -> edit { it.putFloat(key, value) }
        is Long -> edit { it.putLong(key, value) }
        else -> throw UnsupportedOperationException("Not yet implemented")
    }

    inline operator fun <reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T
            = when (T::class) {
        String::class -> getString(key, defaultValue as? String ?: "") as T
        Int::class -> getInt(key, defaultValue as? Int ?: -1) as T
        Boolean::class -> getBoolean(key, defaultValue as? Boolean ?: false) as T
        Float::class -> getFloat(key, defaultValue as? Float ?: -1f) as T
        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T
        else -> throw UnsupportedOperationException("Not yet implemented")
    }

    // Guarda el token en SharedPreferences usando PreferenceHelper
    fun saveToken(context: Context, token: String) {
        defaultPrefs(context)[TOKEN_KEY] = token
    }

    // Obtiene el token guardado en SharedPreferences usando PreferenceHelper
    fun getToken(context: Context): String? {
        return defaultPrefs(context)[TOKEN_KEY]
    }

    // Guarda el estado de autenticación en SharedPreferences como "Autenticado" usando PreferenceHelper
    fun saveAuthenticationState(context: Context, isAuthenticated: Boolean) {
        defaultPrefs(context)[KEY_AUTHENTICATED] = isAuthenticated
    }

    // Obtiene el estado de autenticación guardado en SharedPreferences usando PreferenceHelper
    fun isAuthenticated(context: Context): Boolean {
        return defaultPrefs(context)[KEY_AUTHENTICATED, false]
    }

    // Borra los datos de autenticación guardados en SharedPreferences usando PreferenceHelper
    fun clearAuthenticationData(context: Context) {
        defaultPrefs(context).edit().remove(KEY_AUTHENTICATED).remove(TOKEN_KEY).apply()
    }

    fun saveTokenAndEmail(context: Context, token: String, email: String) {
        defaultPrefs(context)[TOKEN_KEY] = token
        defaultPrefs(context)[KEY_USER_EMAIL] = email
    }

    fun getUserEmail(context: Context): String? {
        return defaultPrefs(context)[KEY_USER_EMAIL]
    }
}

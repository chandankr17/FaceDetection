package com.example.facedetection

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("FaceDetectionPrefs", Context.MODE_PRIVATE)

    fun saveToken(token: String) = prefs.edit().putString("token", token).apply()
    fun getToken(): String? = prefs.getString("token", null)

    fun saveName(name: String) = prefs.edit().putString("name", name).apply()
    fun getName(): String? = prefs.getString("name", null)

    fun saveUserId(userId: String) = prefs.edit().putString("userId", userId).apply()
    fun getUserId(): String? = prefs.getString("userId", null)

    fun clearSession() = prefs.edit().clear().apply()

    fun isLoggedIn(): Boolean = getToken() != null
}
package com.example.facedetection

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private val splashTime: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            val session = SessionManager(this)
            if (session.isLoggedIn()) {
                // Already logged in → go to Home
                startActivity(Intent(this, HomeActivity::class.java))
            } else {
                // Not logged in → go to Login
                startActivity(Intent(this, LoginActivity::class.java))
            }
            finish()
        }, splashTime)
    }
}
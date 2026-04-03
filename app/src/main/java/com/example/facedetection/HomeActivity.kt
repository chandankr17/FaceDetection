package com.example.facedetection

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.TextView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val session = SessionManager(this)

        // Show user name
        val tvWelcome = findViewById<TextView>(R.id.tvWelcome)
        tvWelcome.text = "Hi, ${session.getName() ?: "User"} 👋"

        val btnTakePhoto = findViewById<Button>(R.id.btnTakePhoto)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnTakePhoto.setOnClickListener {
            startActivity(Intent(this, CameraActivity::class.java))
        }

        btnLogout.setOnClickListener {
            session.clearSession()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }
}
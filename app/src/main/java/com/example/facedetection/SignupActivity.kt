package com.example.facedetection

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SignupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnSignup = findViewById<Button>(R.id.btnSignup)
        val tvGoToLogin = findViewById<TextView>(R.id.tvGoToLogin)

        btnSignup.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            btnSignup.isEnabled = false
            btnSignup.text = "Creating account..."

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.api.signup(SignupRequest(name, email, password))
                    val session = SessionManager(this@SignupActivity)
                    session.saveToken(response.token)
                    session.saveName(response.name)
                    session.saveUserId(response.userId)

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SignupActivity, "Account created! Welcome ${response.name}!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignupActivity, HomeActivity::class.java))
                        finishAffinity()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@SignupActivity, "Signup failed: ${e.message}", Toast.LENGTH_SHORT).show()
                        btnSignup.isEnabled = true
                        btnSignup.text = "Sign Up"
                    }
                }
            }
        }

        tvGoToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
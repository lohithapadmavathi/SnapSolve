package com.example.snapsolve

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var rememberCheck: CheckBox
    private lateinit var loginButton: Button
    private lateinit var registerRedirect: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("SnapSolvePrefs", MODE_PRIVATE)

        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        rememberCheck = findViewById(R.id.rememberCheck)
        loginButton = findViewById(R.id.loginButton)
        registerRedirect = findViewById(R.id.registerRedirect)

        // Auto-login if previously remembered
        if (sharedPreferences.getBoolean("remember", false)) {
            val savedEmail = sharedPreferences.getString("email", "")
            val savedPassword = sharedPreferences.getString("password", "")
            if (!savedEmail.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
                goToHome()
            }
        }

        loginButton.setOnClickListener {
            val inputEmail = emailField.text.toString()
            val inputPassword = passwordField.text.toString()
            val savedEmail = sharedPreferences.getString("email", "")
            val savedPassword = sharedPreferences.getString("password", "")

            if (inputEmail == savedEmail && inputPassword == savedPassword) {
                sharedPreferences.edit()
                    .putBoolean("remember", rememberCheck.isChecked)
                    .apply()
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                goToHome()
            } else {
                Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
            }
        }

        registerRedirect.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

package com.example.vmobi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.vmobi.databinding.ActivityLoginBinding
import com.example.vmobi.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.loginButton.setOnClickListener() {
            val username = binding.usernameMain.text.toString()
            val password = binding.passwordMain.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(username,password).addOnCompleteListener() {
                    task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            Log.d("register_username", "User registration successful")
                        } else {
                            Log.e("register_username", "User registration failed", task.exception)
                            Toast.makeText(
                                this,
                                "User registration failed. ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Fill out your Email/Password fields", Toast.LENGTH_SHORT).show()
            }
        }
        binding.SignUp.setOnClickListener {
            val intent = Intent(this, sign_up::class.java)
            startActivity(intent)
            Log.d("register_username", "Sign in button clicked")
        }
    }
}
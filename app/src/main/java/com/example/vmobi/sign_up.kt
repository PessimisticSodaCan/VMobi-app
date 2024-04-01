package com.example.vmobi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vmobi.databinding.ActivitySignUpBinding
import com.example.vmobi.login
import com.google.firebase.auth.FirebaseAuth

class sign_up : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.signInButton.setOnClickListener {
            val intent = Intent(this, login::class.java)
            startActivity(intent)
            Log.d("register_username", "Sign in button clicked")
        }

        binding.nextSignSignUp.setOnClickListener {
            val email = binding.emailSignUp.text.toString()
            val password = binding.passwordSignUp.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, login::class.java)
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
    }
}




package com.example.vmobi

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.vmobi.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.parcelize.Parcelize


@Parcelize
data class loginInfo(
    val email: String,
    val password: String
): Parcelable

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
        binding.test.setOnClickListener {
            val intent = Intent(this, identity_setup::class.java)
            startActivity(intent)
            Log.d("register_username", "Sign in button clicked")
        }

        binding.nextSignSignUp.setOnClickListener {
            val email = binding.emailSignUp.text.toString()
            val password = binding.passwordSignUp.text.toString()

            val user = loginInfo(email, password)




            if (email.isNotEmpty() && password.isNotEmpty()) {
                val intent = Intent(this, identity_setup::class.java)
                intent.putExtra("login", user)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Fill out your Email/Password fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}




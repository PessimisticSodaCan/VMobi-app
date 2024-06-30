package com.example.vmobi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.vmobi.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("create_user")
    suspend fun createUser(@Body user: User?): Response<ResponseBody>
}


object ApiClient {
    private const val BASE_URL = "http://192.168.1.69:3000/"

    // Step 1: Create an instance of HttpLoggingInterceptor
    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Set the desired logging level
    }

    // Step 2: Add the interceptor to OkHttpClient
    val httpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    // Step 3: Build the Retrofit instance with the custom OkHttpClient
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(httpClient)
        .build()

    // Step 4: Create ApiService instance
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
class emergency_contact : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    private lateinit var containerLayout: LinearLayout
    private lateinit var addButton: Button
    private lateinit var createAccountButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.emergency_contact)
        addButton = findViewById(R.id.addContact)
        createAccountButton = findViewById(R.id.create_account)
        containerLayout = findViewById(R.id.container)
        firebaseAuth = FirebaseAuth.getInstance()

        var contactCount = 0

        addButton.setOnClickListener {
            if(contactCount <= 4)
                addEditText()

        }

        createAccountButton.setOnClickListener {
            val user: User? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("user", User::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra("user")
            }

            if (user != null) {
                user.emergencyContacts = retrieveEditTextValues()
            }
            createAccount(user)



        }

    }
    private val editTextList = mutableListOf<EditText>()
    private fun addEditText() {
        val editText = EditText(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.topMargin = resources.getDimensionPixelSize(R.dimen.margin_top_edittext)
        editText.layoutParams = layoutParams
        editText.hint = "Enter text here"
        editText.setBackgroundResource(R.drawable.text_background)
        editText.setTextColor(ContextCompat.getColor(this, R.color.white))

        // Convert 10dp to pixels for padding
        val paddingInDp = 10
        val scale = resources.displayMetrics.density
        val paddingInPx = (paddingInDp * scale + 0.5f).toInt()
        editText.setPadding(paddingInPx, editText.paddingTop, editText.paddingEnd, editText.paddingBottom)

        // Set the text size in sp
        editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)

        // Add the EditText to your container layout
        containerLayout.addView(editText)
        editTextList.add(editText) // Add the EditText to the list for tracking
    }
    private fun retrieveEditTextValues(): List<String> {
        val values = mutableListOf<String>()
        for (editText in editTextList) {
            val text = editText.text.toString()
            values.add(text)
        }
        return values
    }

    private fun createAccount(user: User?) {
        lifecycleScope.launch {
            val success = createUser(user)
            if (success) {
                // Call Firebase authentication if createUser on server was successful
                firebaseAuth.createUserWithEmailAndPassword(user?.username ?: "", user?.password ?: "")
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("register_username", "User registration successful")
                            Toast.makeText(this@emergency_contact, "User created successfully", Toast.LENGTH_SHORT).show()
                            // Navigate to next activity, etc.
                            val intent = Intent(this@emergency_contact, MainActivity::class.java)
                            startActivity(intent)
                            finish()  // Optionally finish the current activity

                        } else {
                            Log.e("register_username", "User registration failed", task.exception)
                            Toast.makeText(
                                this@emergency_contact,
                                "User registration failed. ${task.exception?.message}",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                // Handle failure in creating user on server
                Toast.makeText(this@emergency_contact, "Failed to create user on server", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun createUser(user: User?): Boolean {
        val apiService = ApiClient.apiService

        return try {
            val response = apiService.createUser(user)
            response.isSuccessful  // Return true if request was successful, false otherwise
        } catch (e: Exception) {
            // Handle exception
            e.printStackTrace()
            false
        }
    }

}
package com.example.vmobi

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.vmobi.databinding.ActivityIdentitySetupBinding
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

data class User(
    val Fname: String,
    val Lname: String,
    val username: String,
    val date: String,
    val month: String,
    val year: String,
    val gender: String
)
interface ApiService {
    @POST("create_user")
    suspend fun createUser(@Body user: User): Response<ResponseBody>
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


class identity_setup : AppCompatActivity() {
    private lateinit var binding: ActivityIdentitySetupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)  // Corrected line to set the content view to the inflated binding root

        val button = binding.nextEc

        //test code
        val newUser1 = User("John", "Doe", "john.doe@example.com", "10", "July", "1995", "male")
        createUser(newUser1)

        val newUser2 = User("Alice", "Johnson", "alice.johnson@example.com", "15", "October", "1990", "female")
        createUser(newUser2)

        val newUser3 = User("Michael", "Smith", "michael.smith@example.com", "5", "December", "1988", "male")
        createUser(newUser3)

        val newUser4 = User("Emily", "Brown", "emily.brown@example.com", "20", "April", "1992", "female")
        createUser(newUser4)

        val newUser5 = User("Daniel", "Wilson", "daniel.wilson@example.com", "25", "February", "1985", "male")
        createUser(newUser5)

        button.setOnClickListener {
            val Fname = binding.fname.text.toString()
            val Lname = binding.lname.text.toString()
            val date = binding.Date.text.toString()
            val year = binding.year.text.toString()
            //val month = binding.spinner.toString()
            val checkedChipId = binding.chipGroup.checkedChipId
            val checkedChip = binding.chipGroup.findViewById<Chip>(checkedChipId)
            val gender = checkedChip.text.toString()
            val username = "change later"
            val month = "temp"

            val newUser = User(Fname, Lname, username, date, month, year, gender)
            createUser(newUser)
        }
    }

    private fun createUser(user: User) {
        val apiService = ApiClient.apiService

        lifecycleScope.launch {
            try {
                val response = apiService.createUser(user)
                if (response.isSuccessful) {
                    Toast.makeText(this@identity_setup, "User created successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@identity_setup, "Failed to create user: ${response.errorBody()?.string()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@identity_setup, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

}



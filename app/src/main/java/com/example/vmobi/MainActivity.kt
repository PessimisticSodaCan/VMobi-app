package com.example.vmobi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.Toast

val phoneNumbers = arrayOf("123-456-7890", "987-654-3210", "555-123-4567", "444-555-6666")

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.next_sign_sign_up)

        button.setOnClickListener(View.OnClickListener {
            makeCall()
        })
    }

    private fun makeCall() {
        for (phoneNumber in phoneNumbers) {
            val callSuccessful = initiateCall(phoneNumber)
            if (callSuccessful) {
                Toast.makeText(this, "Call to $phoneNumber was successful.", Toast.LENGTH_SHORT).show()
                break // Exit the loop if the call was successful
            } else {
                Toast.makeText(this, "Call to $phoneNumber failed, trying the next number...", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initiateCall(phoneNumber: String): Boolean {
        return try {
            val phoneIntent = Intent(Intent.ACTION_CALL)
            phoneIntent.data = Uri.parse("tel:$phoneNumber")
            startActivity(phoneIntent)
            true // Assuming the call was successful if no exception was thrown
        } catch (e: Exception) {
            e.printStackTrace()
            false // The call failed if an exception was thrown
        }
    }
}

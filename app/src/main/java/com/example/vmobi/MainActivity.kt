package com.example.vmobi

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

val phoneNumbers = arrayOf("123-456-7890", "987-654-3210", "555-123-4567", "444-555-6666")
//later this will be changed to get numbers from mySql server

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private val requestCallPermission = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.next_sign_sign_up)

        button.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE), requestCallPermission)
            } else {
                makeCall()
            }
        }
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

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCallPermission) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makeCall()
            } else {
                Toast.makeText(this, "Permission denied to make calls", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

package com.example.vmobi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {

    // define objects for edit text and button
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Getting instance of edittext and button
        button = findViewById(R.id.next_sign_sign_up)

        // Attach set on click listener to the button for initiating intent
        button.setOnClickListener(View.OnClickListener {
            // getting phone number from edit text and changing it to String
            val phone_number = "9566169371"

            // Getting instance of Intent with action as ACTION_CALL

            val phone_intent = Intent(Intent.ACTION_DIAL)

            phone_intent.setData(Uri.parse("tel:"+ phone_number));
            // Set data of Intent through Uri by parsing phone number

            // start Intent
            startActivity(phone_intent)
        })
    }
}

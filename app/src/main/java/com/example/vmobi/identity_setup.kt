package com.example.vmobi

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.vmobi.databinding.ActivityIdentitySetupBinding
import com.google.android.material.chip.Chip
import kotlinx.parcelize.Parcelize



@Parcelize
data class User(
    val Fname: String,
    val Lname: String,
    val username: String,
    val date: String,
    val month: String,
    val year: String,
    val gender: String,
    val password: String,
    var emergencyContacts: List<String>?
):Parcelable


class identity_setup : AppCompatActivity() {
    private lateinit var binding: ActivityIdentitySetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIdentitySetupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val button = binding.nextEc

        button.setOnClickListener {

            val info: loginInfo? = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("login", loginInfo::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra("login")
            }

            val Fname = binding.fname.text.toString()
            val Lname = binding.lname.text.toString()
            val date = binding.Date.text.toString()
            val year = binding.year.text.toString()
            val checkedChipId = binding.chipGroup.checkedChipId
            val checkedChip = binding.chipGroup.findViewById<Chip>(checkedChipId)
            val gender = checkedChip.text.toString()
            val month = "temp"
            val username = info?.email ?: ""

            if (username.isBlank()) {
                Log.d("identity_setup", "Email cannot be empty")
                return@setOnClickListener
            }
            val password = info?.password ?: ""

            if (password.isBlank()) {
                Log.d("identity_setup", "password cannot be empty")
                return@setOnClickListener
            }


            val newUser = User(Fname, Lname, username, date, month, year, gender,password, null)

            val intent = Intent(this, emergency_contact::class.java)
            intent.putExtra("user", newUser)
            startActivity(intent)
        }
    }
}

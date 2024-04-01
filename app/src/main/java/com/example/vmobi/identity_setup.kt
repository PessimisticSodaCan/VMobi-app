package com.example.vmobi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.vmobi.databinding.ActivityIdentitySetupBinding
import com.google.android.material.chip.Chip

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class identity_setup : AppCompatActivity() {
    private lateinit var binding: ActivityIdentitySetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityIdentitySetupBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_identity_setup)

        val button = binding.nextEc




        button.setOnClickListener{
            var Fname = binding.fname.text.toString()
            var Lname = binding.fname.text.toString()
            var date = binding.Date.text.toString()
            var year = binding.year.text.toString()
            val checkedChipId = binding.chipGroup.checkedChipId
            val checkedChip = binding.chipGroup.findViewById<Chip>(checkedChipId)
            val gender = checkedChip.text.toString()
        }

    }
}
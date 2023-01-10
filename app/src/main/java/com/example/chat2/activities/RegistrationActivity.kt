package com.example.chat2.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.chat2.R
import com.example.chat2.databinding.ActivityStartBinding
import com.example.chat2.fragments.AuthenticationFragment

class RegistrationActivity: AppCompatActivity(){
    private lateinit var binding: ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, AuthenticationFragment.newInstance())
            .commit()
    }
}
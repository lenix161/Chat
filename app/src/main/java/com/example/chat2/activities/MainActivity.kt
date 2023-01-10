package com.example.chat2.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chat2.R
import com.example.chat2.databinding.ActivityMainBinding
import com.example.chat2.fragments.ChatsFragment
import com.example.chat2.fragments.FriendsFragment
import com.example.chat2.fragments.ProfileFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmentContainer = R.id.fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Switching pages
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId){
                R.id.friens -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(fragmentContainer, FriendsFragment.newInstance())
                        .commit()
                }

                R.id.chats -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(fragmentContainer, ChatsFragment.newInstance())
                        .commit()
                }

                R.id.profile -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(fragmentContainer, ProfileFragment.newInstance())
                        .commit()
                }
            }
        true
        }

        //Default page
        binding.bottomNavigation.selectedItemId = R.id.chats
    }
}
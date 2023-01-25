package com.example.chat2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat2.databinding.FriendsFragmentBinding
import com.example.chat2.model.User
import com.example.chat2.recyclerViewChat.Data
import com.example.chat2.recyclerViewChat.FriendsAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FriendsFragment: Fragment() {
    private lateinit var binding: FriendsFragmentBinding
    private val adapter = FriendsAdapter()
    private lateinit var databaseUsers: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FriendsFragmentBinding.inflate(inflater, container, false)

        binding.friendsRcView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        binding.friendsRcView.adapter = adapter

        databaseUsers = Firebase
            .database("https://database-91987-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("users")

        readDataFromDB()


        return binding.root


    }

    /** Get friends list from DB */
    private fun readDataFromDB(){
        val eventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(Data.friendsList.size > 0) //update
                    Data.friendsList.clear()

                for (item in snapshot.children){
                    val usr = item.getValue(User::class.java)
                    if(usr != null){
                        adapter.addFriendToList(usr)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        databaseUsers.addValueEventListener(eventListener)
    }

    companion object{
        @JvmStatic
        fun newInstance() = FriendsFragment()
    }
}
package com.example.chat2.fragments

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chat2.R
import com.example.chat2.databinding.ProfileFragmentBinding
import com.example.chat2.model.Message
import com.example.chat2.recyclerView.Data
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase

class ProfileFragment: Fragment() {
    private lateinit var binding: ProfileFragmentBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseUsers: DatabaseReference
    private lateinit var userName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)

        databaseUsers = Firebase
            .database("https://database-91987-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("users")

        auth = Firebase.auth

        getNameFromDB()

        binding.editUsernameButton.setOnClickListener { startEditName() }
        binding.saveNewUsernameButton.setOnClickListener { saveNewName() }

        return binding.root
    }

    private fun getNameFromDB(){
        val eventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val email = item.child("email").value
                    if(email != null && email == auth.currentUser?.email){
                        userName = item.child("userName").value.toString()
                        binding.userName.text = userName
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        databaseUsers.addValueEventListener(eventListener)
    }

    private fun startEditName(){
        binding.userName.visibility = View.GONE
        binding.editUsernameButton.visibility = View.GONE
        binding.editUsernameField.visibility = View.VISIBLE
        binding.saveNewUsernameButton.visibility = View.VISIBLE
        binding.editUsernameField.hint = userName
    }

    private fun saveNewName(){
        if (binding.editUsernameField.text.isBlank()){
            binding.editUsernameField.requestFocus()
            Toast.makeText(context, "Поле не может быть пустым!", Toast.LENGTH_SHORT).show()
        } else {
            val newName = binding.editUsernameField.text.toString()
            setNewNameToDB(newName)
            binding.editUsernameField.text.clear()
            endEditName()
        }
    }

    private fun endEditName(){
        binding.userName.visibility = View.VISIBLE
        binding.editUsernameButton.visibility = View.VISIBLE
        binding.editUsernameField.visibility = View.GONE
        binding.saveNewUsernameButton.visibility = View.GONE
        getNameFromDB()
    }

    private fun setNewNameToDB(newName: String){
        auth.currentUser?.email?.let {
            databaseUsers
                .child(it.substringBefore("@"))
                .child("userName")
                .setValue(newName)
        }
    }

    companion object{
        @JvmStatic
        fun newInstance() = ProfileFragment()
    }
}
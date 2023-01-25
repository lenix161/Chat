package com.example.chat2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat2.databinding.ChatsFragmentBinding
import com.example.chat2.model.Message
import com.example.chat2.recyclerViewChat.Data
import com.example.chat2.recyclerViewChat.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatsFragment: Fragment() {
    private lateinit var binding: ChatsFragmentBinding
    private val adapter = MessageAdapter()
    private lateinit var databaseMessages: DatabaseReference
    private lateinit var databaseUsers: DatabaseReference
    private lateinit var auth: FirebaseAuth
    public lateinit var userName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatsFragmentBinding.inflate(inflater, container, false)
        auth = Firebase.auth

        databaseMessages = Firebase
            .database("https://database-91987-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("messages")

        databaseUsers = Firebase
            .database("https://database-91987-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("users")
        getNameFromDB()

        binding.messagesRcView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        binding.messagesRcView.adapter = adapter

        readDataFromDB()
        //getNameFromDB()

        binding.sendBtn.setOnClickListener { onClickMessageSend(userName) }

        return binding.root
    }


    /** Send message to DB */
    private fun onClickMessageSend(userName: String){
        if (binding.editText.text.isNotEmpty()){
            val msgText = binding.editText.text.toString()
            val usrSenderId = auth.currentUser?.uid.toString()
            val msgInstance = Message( usrSenderId, userName, msgText, Message.getTime())

            databaseMessages.push().setValue(msgInstance)

            binding.editText.text.clear()
        }
    }

    /** Get messages from DB */
    private fun readDataFromDB(){
        val eventListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(Data.messageList.size > 0) //update
                    Data.messageList.clear()

                for (item in snapshot.children){
                    val msg = item.getValue(Message::class.java)
                    if(msg != null){
                        adapter.addMessageToList(msg)
                    }
                }
                binding.messagesRcView.scrollToPosition(adapter.itemCount-1)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        databaseMessages.addValueEventListener(eventListener)
    }

    /** Get user name from DB */
    private fun getNameFromDB(){
        val eventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val email = item.child("email").value
                    if(email != null && email == auth.currentUser?.email){
                        val name = item.child("userName").value.toString()
                        userName = name
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
        fun newInstance() = ChatsFragment()
    }
}
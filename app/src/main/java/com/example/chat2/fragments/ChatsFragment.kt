package com.example.chat2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chat2.databinding.ChatsFragmentBinding
import com.example.chat2.model.Message
import com.example.chat2.recyclerView.Data
import com.example.chat2.recyclerView.MessageAdapter
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChatsFragment: Fragment() {
    private lateinit var binding: ChatsFragmentBinding
    private val adapter = MessageAdapter()
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ChatsFragmentBinding.inflate(inflater, container, false)

        database = Firebase
            .database("https://database-91987-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("messages")

        binding.messagesRcView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL, false)
        binding.messagesRcView.adapter = adapter

        readDataFromDB()

        val usersDB = Firebase
            .database("https://database-91987-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference("users")

        val snap = usersDB.child("")

        binding.sendBtn.setOnClickListener { onClickMessageSend() }

        return binding.root
    }


    /** Send message to DB */
    private fun onClickMessageSend(){
        if (binding.editText.text.isNotEmpty()){
            val msgText = binding.editText.text.toString()
            val msgInstance = Message("User", msgText, Message.getTime())

            database.push().setValue(msgInstance)

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
                        adapter.addMessage(msg)
                    }
                }
                binding.messagesRcView.scrollToPosition(adapter.itemCount-1)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        database.addValueEventListener(eventListener)
    }

    companion object{
        @JvmStatic
        fun newInstance() = ChatsFragment()
    }
}
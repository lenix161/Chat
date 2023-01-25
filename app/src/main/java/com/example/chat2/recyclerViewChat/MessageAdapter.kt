package com.example.chat2.recyclerViewChat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chat2.R
import com.example.chat2.databinding.RecivedMessageItemBinding
import com.example.chat2.databinding.SendedMessageItemBinding
import com.example.chat2.model.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val ITEM_SENT = 1
    val ITEM_RECIVED = 2

    class ReciveMessageHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = RecivedMessageItemBinding.bind(item)
        fun bind(msg: Message){
            binding.userName.text = msg.senderName
            binding.messageText.text = msg.messageText
            binding.messageTime.text = msg.sendTime
        }
    }

    class SendMessageHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = SendedMessageItemBinding.bind(item)
        fun bind(msg: Message){
            binding.userName.text = msg.senderName
            binding.messageText.text = msg.messageText
            binding.messageTime.text = msg.sendTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if(viewType == ITEM_SENT){
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.sended_message_item, parent, false)
            return SendMessageHolder(view)
        } else {
            val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.recived_message_item, parent, false)
            return ReciveMessageHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder.javaClass == SendMessageHolder::class.java){
            val viewHolder = holder as SendMessageHolder
            viewHolder.bind(Data.messageList[position])
        } else {
            val viewHolder = holder as ReciveMessageHolder
            viewHolder.bind(Data.messageList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = Data.messageList[position]
        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderUserId)){
            return ITEM_SENT
        } else {
            return ITEM_RECIVED
        }
    }

    override fun getItemCount(): Int {
        return Data.messageList.size

    }

    fun addMessageToList(msg: Message){
        Data.messageList.add(msg)
        notifyDataSetChanged()
    }


}
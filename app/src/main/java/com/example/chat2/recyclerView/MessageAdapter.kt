package com.example.chat2.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat2.R
import com.example.chat2.databinding.MessageItemBinding
import com.example.chat2.model.Message

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageHolder>() {

    class MessageHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = MessageItemBinding.bind(item)
        fun bind(msg: Message){
            binding.userName.text = msg.senderName
            binding.messageText.text = msg.messageText
            binding.messageTime.text = msg.sendTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.message_item, parent, false)

        return MessageHolder(view)
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.bind(Data.messageList[position])
    }

    override fun getItemCount(): Int {
        return Data.messageList.size
    }

    fun addMessage(msg: Message){
        Data.messageList.add(msg)
        notifyDataSetChanged()
    }
}
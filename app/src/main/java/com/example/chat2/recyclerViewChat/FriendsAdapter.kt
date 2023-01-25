package com.example.chat2.recyclerViewChat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.chat2.R
import com.example.chat2.databinding.FriendItemBinding
import com.example.chat2.model.User

class FriendsAdapter: RecyclerView.Adapter<FriendsAdapter.FriendsHolder>() {

    class FriendsHolder(item: View): RecyclerView.ViewHolder(item){
        private val binding = FriendItemBinding.bind(item)
        fun bind(usr: User){
            binding.userName.text = usr.userName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.friend_item, parent, false)
        return FriendsHolder(view)
    }

    override fun onBindViewHolder(holder: FriendsHolder, position: Int) {
        holder.bind(Data.friendsList[position])
    }

    override fun getItemCount(): Int {
        return Data.friendsList.size
    }

    fun addFriendToList(usr: User){
        Data.friendsList.add(usr)
        notifyDataSetChanged()
    }
}
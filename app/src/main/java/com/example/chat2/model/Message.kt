package com.example.chat2.model

import java.text.SimpleDateFormat
import java.util.*

class Message(val senderUserId: String, val senderName: String, val messageText: String, val sendTime: String){

    constructor():this ("","","","")

    companion object{
        /**Return time in format (HH:mm)*/
        fun getTime(): String {
            val dateFormat = SimpleDateFormat("HH:mm")
            return dateFormat.format(Date())
        }
    }

}


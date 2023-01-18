package com.example.chat2.model

data class User(
    val email: String,
    val userName: String,){
    constructor(): this("", "")
}

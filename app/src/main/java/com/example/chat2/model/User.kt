package com.example.chat2.model

data class User(
    val login: String,
    val name: String,
    val password: String){
    constructor(): this("", "", "")
}

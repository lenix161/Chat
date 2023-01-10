package com.example.chat2.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.chat2.activities.MainActivity
import com.example.chat2.databinding.FragmentAuthentificationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class AuthenticationFragment: Fragment() {
    private lateinit var binding: FragmentAuthentificationBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthentificationBinding.inflate(inflater, container, false)

        auth = Firebase.auth

        binding.buttonLogin.setOnClickListener { login() }
        binding.buttonRegister.setOnClickListener { register() }

        return binding.root
    }

    private fun login(){
        if (binding.insertLogin.text.isEmpty()){
            Toast.makeText(context, "Email не может быть пустым", Toast.LENGTH_SHORT).show()
            binding.insertLogin.requestFocus()
        } else if (binding.insertPassword.text.isEmpty()){
            Toast.makeText(context, "Пароль не может быть пустым", Toast.LENGTH_SHORT).show()
            binding.insertPassword.requestFocus()
        } else {
            auth.signInWithEmailAndPassword(binding.insertLogin.text.toString(),
                binding.insertPassword.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                } else {
                    Toast.makeText(context, "Неправильно введен логин или пароль", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun register(){
        if (binding.insertLogin.text.isEmpty()){
            Toast.makeText(context, "Email не может быть пустым", Toast.LENGTH_SHORT).show()
            binding.insertLogin.requestFocus()
        } else if (binding.insertPassword.text.isEmpty()){
            Toast.makeText(context, "Пароль не может быть пустым", Toast.LENGTH_SHORT).show()
            binding.insertPassword.requestFocus()
        } else {
            auth.createUserWithEmailAndPassword(binding.insertLogin.text.toString(),
                binding.insertPassword.text.toString()).addOnCompleteListener { task ->
                if (task.isSuccessful){
                    startActivity(Intent(activity, MainActivity::class.java))
                    activity?.finish()
                } else {
                    Toast.makeText(context, "Неправильно введен логин или пароль", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }

    companion object{
        @JvmStatic
        fun newInstance() = AuthenticationFragment()
    }
}
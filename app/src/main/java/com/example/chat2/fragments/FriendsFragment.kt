package com.example.chat2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.chat2.R

class FriendsFragment: Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frends_fragment, container, false)
    }

    companion object{
        @JvmStatic
        fun newInstance() = FriendsFragment()
    }
}
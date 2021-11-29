package com.nhom2.bedatabase.presentation.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nhom2.bedatabase.databinding.LoadingScreenBinding

class LoadingScreen : DialogFragment(){
    private lateinit var binding: LoadingScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoadingScreenBinding.inflate(inflater,container,false)
        return binding.root
    }
}
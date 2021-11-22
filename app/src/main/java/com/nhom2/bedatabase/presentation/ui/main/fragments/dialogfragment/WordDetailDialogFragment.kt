package com.nhom2.bedatabase.presentation.ui.main.fragments.dialogfragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.nhom2.bedatabase.databinding.DialogWordDetailBinding

class WordDetailDialogFragment : DialogFragment(){
    lateinit var binding: DialogWordDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogWordDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnEditWord.setOnClickListener{
        }
    }
}
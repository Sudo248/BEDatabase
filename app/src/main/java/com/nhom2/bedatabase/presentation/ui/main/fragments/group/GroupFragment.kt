package com.nhom2.bedatabase.presentation.ui.main.fragments.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentGroupBinding
import com.nhom2.bedatabase.presentation.ui.main.MainActivity

class GroupFragment : Fragment() {
    lateinit var binding: FragmentGroupBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observer()
    }

    private fun observer() {
        
    }

    private fun setUpUi(){
        (activity as MainActivity).showAddFabButton(true)
    }

}
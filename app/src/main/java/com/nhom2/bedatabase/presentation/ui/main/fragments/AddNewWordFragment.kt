package com.nhom2.bedatabase.presentation.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentAddNewWordBinding
import com.nhom2.bedatabase.presentation.ui.main.MainActivity

class AddNewWordFragment : Fragment() {

    private lateinit var binding: FragmentAddNewWordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNewWordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun setUpUi() {
        (activity as MainActivity).showAddFabButton(false)
        (activity as MainActivity).setUpViewFullScreen()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).resetView()
    }

}
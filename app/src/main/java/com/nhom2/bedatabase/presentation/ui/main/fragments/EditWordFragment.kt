package com.nhom2.bedatabase.presentation.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentEditWordBinding

class EditWordFragment : Fragment() {

    lateinit var binding: FragmentEditWordBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //truyen bundle de xem la edit word, add new word hay add new word to group
        super.onViewCreated(view, savedInstanceState)
        val spinnerAdapter = ArrayAdapter(requireContext(), R.layout.spinner_type_item, resources.getStringArray(R.array.Types))
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.spnType.adapter = spinnerAdapter

    }
}
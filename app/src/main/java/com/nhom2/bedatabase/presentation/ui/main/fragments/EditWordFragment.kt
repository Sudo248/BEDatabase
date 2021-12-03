package com.nhom2.bedatabase.presentation.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentEditWordBinding
import com.nhom2.bedatabase.domain.models.Eng
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel

class EditWordFragment : Fragment() {

    private lateinit var binding: FragmentEditWordBinding
    private val viewModel: MainViewModel by activityViewModels()
    private var vocabulary: Eng? = null

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

        setUpUi()
        observer()
    }

    private fun observer() {

    }

    private fun setUpUi() {
        (activity as MainActivity).setUpViewFullScreen()
        val spinnerAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.Types, R.layout.spinner_type_item)
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.spnType.adapter = spinnerAdapter

        vocabulary = viewModel.currentVocabulary

        vocabulary?.let {
            setVocabularyInfo()
        }

    }

    private fun setVocabularyInfo(){
        with(binding){
            edtEnglishWord.setText(vocabulary!!.content)
            edtVnWord.setText(vocabulary!!.vns[0].content)
            edtPronunciation.setText(vocabulary!!.pronunciation)
            tvGroupType.text = viewModel.getGroupNameById(vocabulary!!.group_id)


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).resetView()
    }


}
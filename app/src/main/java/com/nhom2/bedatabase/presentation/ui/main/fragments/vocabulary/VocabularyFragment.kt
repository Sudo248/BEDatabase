package com.nhom2.bedatabase.presentation.ui.main.fragments.vocabulary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentVocabularyBinding
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel
import com.nhom2.bedatabase.presentation.ui.main.adapter.VocabularyAdapter

class VocabularyFragment : Fragment() {

    private lateinit var binding: FragmentVocabularyBinding

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: VocabularyAdapter

    private val TAG = "VocabularyFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentVocabularyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observer()
    }

    private fun observer() {
        viewModel.vocabularies.observe(viewLifecycleOwner){
            adapter.submitList(it.toList())
        }
    }

    private fun setUpUi() {
        adapter = VocabularyAdapter(
            onEditEngClick = {
                onEditEngClick(it)
            },
            onDeleteEngClick = {
                onDeleteEngClick(it)
            }
        )
        binding.rcvVocabularyList.adapter = adapter
    }

    private fun onEditEngClick(pos: Int){
        viewModel.setCurrentVocabulary(pos)
        (activity as MainActivity).navigate(R.id.action_vocabularyFragment_to_editWordFragment)
    }

    private fun onDeleteEngClick(pos: Int){
        viewModel.deleteVocabulary(pos)
    }

}
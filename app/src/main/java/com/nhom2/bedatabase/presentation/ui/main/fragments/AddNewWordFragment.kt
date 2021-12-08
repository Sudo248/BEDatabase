package com.nhom2.bedatabase.presentation.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentAddNewWordBinding
import com.nhom2.bedatabase.domain.common.Constants.ADD_ACTION
import com.nhom2.bedatabase.presentation.ui.main.LoadingScreen
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel

class AddNewWordFragment : Fragment() {

    private lateinit var binding: FragmentAddNewWordBinding

    private val viewModel by activityViewModels<MainViewModel>()

    private val loadingScreen by lazy{ LoadingScreen() }

    private var current_group_id: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            current_group_id = it.getInt(ADD_ACTION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddNewWordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observer()
    }

    private fun observer() {
        viewModel.isLoading.observe(viewLifecycleOwner){
            if(it){
                loadingScreen.show(childFragmentManager, null)
            }else{
                if(loadingScreen.isVisible) loadingScreen.dismiss()
            }
        }
    }

    private fun setUpUi() {
        (activity as MainActivity).showAddFabButton(false)
        (activity as MainActivity).setUpViewFullScreen()
        current_group_id?.let {
            binding.tvAddNewGroupType.text = viewModel.getGroupNameById(it)
        }

        binding.btnConfirm.setOnClickListener {
            getVocabulary()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).resetView()
    }

    private fun getVocabulary(){
        with(binding){
//            viewModel.postVocabulary(
//                group_id = spnAddNewType.selectedItem.toString()
//            )
        }
    }

}
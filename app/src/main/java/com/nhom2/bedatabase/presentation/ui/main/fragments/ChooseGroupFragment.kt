package com.nhom2.bedatabase.presentation.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.databinding.FragmentChooseGroupBinding
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel
import com.nhom2.bedatabase.presentation.ui.main.adapter.GroupAdapter

class ChooseGroupFragment : Fragment() {
    private lateinit var binding: FragmentChooseGroupBinding
    val viewModel by activityViewModels<MainViewModel>()
    lateinit var adapter: GroupAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChooseGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observer()
    }

    private fun observer() {
        viewModel.groups.observe(viewLifecycleOwner){
            adapter.submitList(it.toList())
        }
    }

    private fun setUpUi() {
        adapter = GroupAdapter(
            onEditGroupClick = {
            },
            onOpenGroup = {
                onOpenGroup(it)
            },
            isEnableEditAndDelete = false
        )
        with(binding){
            rcvGroupList.adapter = adapter
        }
    }

    private fun onOpenGroup(group_id: Int){
        viewModel.setGroupForCurrentVocabulary(group_id)
        (activity as MainActivity).onBackPressed()
    }
}
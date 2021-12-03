package com.nhom2.bedatabase.presentation.ui.main.fragments.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentGroupBinding
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel
import com.nhom2.bedatabase.presentation.ui.main.adapter.GroupAdapter
import com.nhom2.bedatabase.presentation.ui.main.fragments.dialogfragment.AddNewGroupDialogFragment

class GroupFragment : Fragment() {
    lateinit var binding: FragmentGroupBinding
    val viewModel by activityViewModels<MainViewModel>()
    lateinit var adapter: GroupAdapter

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
        viewModel.groups.observe(viewLifecycleOwner){
            adapter.submitList(it.toList())
        }
    }

    private fun setUpUi() {
        adapter = GroupAdapter(
            onEditGroupClick = {
                onEditGroupClick(it)
            },
            onDeleteGroupClick = {
                onDeleteGroupClick(it)
            },
            onOpenGroup = {
                onOpenGroup(it)
            }
        )
    }

    private fun onEditGroupClick(pos: Int){
        viewModel.setCurrentGroup(pos)
        //TODO: Edit group
    }

    private fun onDeleteGroupClick(pos: Int) {
        viewModel.deleteGroup(pos)
    }

    private fun onOpenGroup(pos: Int){
        //TODO: Open Group to display vocabulary
    }
}
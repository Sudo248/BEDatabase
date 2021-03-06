package com.nhom2.bedatabase.presentation.ui.main.fragments.group

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentGroupBinding
import com.nhom2.bedatabase.domain.common.Constants.CURRENT_GROUP_ID
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel
import com.nhom2.bedatabase.presentation.ui.main.adapter.GroupAdapter
import com.nhom2.bedatabase.presentation.ui.main.fragments.dialogfragment.AddNewGroupDialogFragment

class GroupFragment : Fragment() {
    lateinit var binding: FragmentGroupBinding
    val viewModel by activityViewModels<MainViewModel>()
    lateinit var adapter: GroupAdapter
    val addNewGroupDialogFragment by lazy { AddNewGroupDialogFragment() }

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
        (activity as MainActivity).showAddFabButton(true)
        (activity as MainActivity).setAddOnClickListener {
            addNewGroupDialogFragment.show(childFragmentManager, null)
        }
        adapter = GroupAdapter(
            onEditGroupClick = {
                onEditGroupClick(it)
            },
            onOpenGroup = {
                onOpenGroup(it)
            }
        )
        binding.rcvGroupList.adapter = adapter
    }

    private fun onEditGroupClick(pos: Int){
        viewModel.setCurrentGroup(pos)
        //TODO: Edit group
    }

    private fun onDeleteGroupClick(pos: Int) {
        viewModel.deleteGroup(pos)
    }

    private fun onOpenGroup(group_id: Int){
        val bundle = Bundle()
        bundle.putInt(CURRENT_GROUP_ID, group_id)
        (activity as MainActivity).navigate(R.id.action_groupFragment_to_vocabularyFragment, bundle)
    }
}
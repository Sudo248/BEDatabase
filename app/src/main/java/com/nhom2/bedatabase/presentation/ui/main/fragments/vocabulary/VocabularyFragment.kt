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
import com.nhom2.bedatabase.domain.common.Constants.ADD_ACTION
import com.nhom2.bedatabase.domain.common.Constants.CURRENT_GROUP_ID
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel
import com.nhom2.bedatabase.presentation.ui.main.adapter.VocabularyAdapter
import com.nhom2.bedatabase.presentation.ui.main.fragments.dialogfragment.WordDetailDialogFragment

class VocabularyFragment : Fragment() {

    private lateinit var binding: FragmentVocabularyBinding

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var adapter: VocabularyAdapter

    private var current_group_id: Int? = null

    private val TAG = "VocabularyFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            current_group_id = it.getInt(CURRENT_GROUP_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
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
        viewModel.vocabularies.observe(viewLifecycleOwner){ list ->
            Log.d(TAG, "current_group_id: $current_group_id")
            if(current_group_id != null){
                val vocabularyOfGroup = list.filter { it.group_id == current_group_id }
                adapter.submitList(vocabularyOfGroup)
            }
            else adapter.submitList(list.toList())
        }
    }

    private fun setUpUi() {
        (activity as MainActivity).showAddFabButton(true)
        (activity as MainActivity).setAddOnClickListener {
            Log.d(TAG, "setUpUi: addOnClick")

            if(current_group_id == null){
                (activity as MainActivity).navigate(R.id.action_vocabularyFragment_to_addNewWordFragment)
            }else{
                val bundle = Bundle()
                bundle.putInt(ADD_ACTION, current_group_id!!)
                (activity as MainActivity).navigate(R.id.action_vocabularyFragment_to_addNewWordFragment, bundle)
            }
        }
        adapter = VocabularyAdapter(
            onEditEngClick = {
                onEditEngClick(it)
            },
            onDeleteEngClick = {
                onDeleteEngClick(it)
            },
            onEngClick = {
                onOpenVocabularyDetail(it)
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

    private fun onOpenVocabularyDetail(pos: Int){
        viewModel.setCurrentVocabulary(pos)
        val detailPopUp = WordDetailDialogFragment{
            (activity as MainActivity).navigate(R.id.action_vocabularyFragment_to_editWordFragment)
        }
        detailPopUp.show(childFragmentManager, null)


    }

}
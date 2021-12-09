package com.nhom2.bedatabase.presentation.ui.main.fragments.dialogfragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.databinding.DialogWordDetailBinding
import com.nhom2.bedatabase.domain.models.Eng
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel

class WordDetailDialogFragment(
    private val onClickEditWord: ()-> Unit
) : DialogFragment(){
    lateinit var binding: DialogWordDetailBinding
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogWordDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnEditWord.setOnClickListener{
            onClickEditWord()
            this.dismiss()
        }
        binding.btnBack.setOnClickListener {
            dismiss()
        }

        observer()

    }

    private fun observer(){
        viewModel.currentVocabulary.observe(viewLifecycleOwner){ eng ->
            eng?.let{
                var vns = ""
                for(i in it.vns){
                    vns += i.content + ", "
                }
                vns.dropLast(2)
                with(binding){
                    tvContent.text = it.content
                    tvPronunciation.text = it.pronunciation
                    tvVnMeaning.text = vns
                    tvGroupType.text = viewModel.getGroupNameById(it.group_id)
                    tvType.text = it.type
                }
            }
        }
    }

}
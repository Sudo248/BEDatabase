package com.nhom2.bedatabase.presentation.ui.main.fragments.dialogfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.databinding.DialogAddGroupBinding
import com.nhom2.bedatabase.domain.models.Group
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel

class AddNewGroupDialogFragment : DialogFragment() {
    lateinit var binding: DialogAddGroupBinding
    val viewModel by activityViewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogAddGroupBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
    }

    private fun setUpUi() {
        with(binding){
            btnCancel.setOnClickListener {
                if (isVisible) dismiss()
            }
            edtGroupName.doOnTextChanged { text, _, _, _ ->
                if (!text.isNullOrBlank()){
                    btnConfirm.visibility = View.VISIBLE
                }
            }
            btnConfirm.setOnClickListener {
                viewModel.addGroup(Group(
                    name = edtGroupName.text.toString(),
                    description = edtGroupDescription.text.toString()
                ))
            }
        }
    }
}
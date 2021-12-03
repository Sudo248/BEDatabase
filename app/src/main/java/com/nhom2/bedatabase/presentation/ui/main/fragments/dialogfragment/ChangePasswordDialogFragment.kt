package com.nhom2.bedatabase.presentation.ui.main.fragments.dialogfragment

import android.os.Bundle
import com.nhom2.bedatabase.domain.common.Result
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.databinding.DialogChangePasswordBinding
import com.nhom2.bedatabase.presentation.ui.main.LoadingScreen
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel

class ChangePasswordDialogFragment : DialogFragment (){
    lateinit var binding: DialogChangePasswordBinding
    val viewModel by activityViewModels<MainViewModel>()
    private val TAG = "change password dialog"
    private val loadingScreen by lazy { LoadingScreen() }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogChangePasswordBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUi()
        observer()
    }

    private fun observer() {
        viewModel.result.observe(viewLifecycleOwner){
            when (it){
                is Result.Loading ->{
                    loadingScreen.show(childFragmentManager, null)
                }
                is Result.Error -> {
                    if (loadingScreen.isVisible) loadingScreen.dismiss()
                    binding.tvErrorChangePassword.text = it.message
                }
                else -> {
                    if (loadingScreen.isVisible) loadingScreen.dismiss()
                    dismiss()
                }
            }
        }
    }

    private fun setUpUi() {
        with(binding){
            btnCancel.setOnClickListener {
                dismiss()
            }
            btnConfirm.setOnClickListener {
                viewModel.changePassword(edtOldPassword.text.toString(),edtNewPassword.text.toString())
            }
            edtConfirmPassword.doOnTextChanged { text, _, _, _ ->
                Log.e(TAG, text.toString() + " " + edtNewPassword.text.toString() )
                if (!text.isNullOrBlank() && text.toString() == edtNewPassword.text.toString()){
                    btnConfirm.visibility = View.VISIBLE
                }
            }
        }
    }


}
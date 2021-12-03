package com.nhom2.bedatabase.presentation.ui.main.fragments.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentProfileBinding
import com.nhom2.bedatabase.domain.common.Constants
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.presentation.ui.main.LoadingScreen
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel
import com.nhom2.bedatabase.presentation.ui.main.fragments.dialogfragment.ChangePasswordDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
@AndroidEntryPoint
class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private val viewModel by activityViewModels<MainViewModel>()
    private val loadingScreen by lazy {LoadingScreen()}
    private val changePasswordDialogFragment by lazy {ChangePasswordDialogFragment()}
    private var pathImg: String? = null
    private var isUpdate = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        catchAction()
        observer()
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun catchAction() {
        (activity as MainActivity).setUpViewFullScreen()
        with(binding){
            btnEditUserAvatar.setOnClickListener {
                val intent = Intent(Intent.ACTION_GET_CONTENT)
                intent.type = "image/*"
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.REQUEST_SELECT_AVATAR)
            }
            btnEditUserName.setOnClickListener {
                if (!edtNameUser.isFocusable) {
                    edtNameUser.isFocusable = true
                    edtNameUser.isFocusableInTouchMode = true
                    edtNameUser.isClickable = true
                }
            }
            root.setOnClickListener {
                if (edtNameUser.isFocusable){
                    edtNameUser.isFocusable = false
                    edtNameUser.isFocusableInTouchMode = false
                    edtNameUser.isClickable = false
                    edtNameUser.clearFocus()
                }
            }
            cardSaveChange.setOnClickListener {
                isUpdate = true
                viewModel.updateUser(edtNameUser.text.toString(), pathImg)
            }
            cardSignOut.setOnClickListener {
                viewModel.signOut()
                (activity as MainActivity).backToSignActivity()
            }
            cardChangePassword.setOnClickListener {
                changePasswordDialogFragment.show(childFragmentManager, null)

            }
        }

    }
    private fun observer() {
        viewModel.isLoading.observe(viewLifecycleOwner){
            if (it)
                loadingScreen.show(childFragmentManager, null)
            else {
                if (loadingScreen.isVisible) loadingScreen.dismiss()
                if (isUpdate) (activity as MainActivity).navigate(R.id.action_profileFragment_to_homeFragment)
            }
        }
        viewModel.user.observe(viewLifecycleOwner){
            Log.e("info", "observer: $it", )
            binding.edtNameUser.setText(it.user_name)
            it.path_image?.let {
                lifecycleScope.launch(Dispatchers.IO) {
                    val imgFile = File(it)
                    withContext(Dispatchers.Main) {
//                        binding.imgAvatar.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        (activity as MainActivity).resetView()
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.REQUEST_SELECT_AVATAR){
            data?.let{
                pathImg= it.data?.path
                Log.e("path image", "$pathImg" )

                binding.imgAvatar.setImageURI(it.data)
            }
        }
    }
}
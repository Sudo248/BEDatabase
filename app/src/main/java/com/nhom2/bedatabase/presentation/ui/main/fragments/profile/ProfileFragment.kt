package com.nhom2.bedatabase.presentation.ui.main.fragments.profile

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.text.Editable
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
import com.nhom2.bedatabase.presentation.ui.main.view_models.ProfileViewModel
import com.nhom2.bedatabase.domain.common.Result
import com.nhom2.bedatabase.presentation.ui.main.LoadingScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
@AndroidEntryPoint
class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private val viewModel by activityViewModels<ProfileViewModel>()
    private val loadingScreen by lazy {LoadingScreen()}

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
        }

    }
    private fun observer() {
        viewModel.result.observe(viewLifecycleOwner){
            when(it){
                is Result.Loading -> {
                    loadingScreen.show(childFragmentManager, null)
                }
                is Result.Error -> {
                    if (loadingScreen.isVisible) loadingScreen.dismiss()
                }
                else -> {

                }
            }
        }
        viewModel.user.observe(viewLifecycleOwner){
            binding.edtNameUser.setText(it.user_name)
            it.path_image?.let {
                lifecycleScope.launch(Dispatchers.IO) {
                    val imgFile = File(it)
                    withContext(Dispatchers.Main) {
                        if (imgFile.exists()) {
                            binding.imgAvatar.setImageBitmap(BitmapFactory.decodeFile(imgFile.absolutePath))
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        (activity as MainActivity).resetView()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.REQUEST_SELECT_AVATAR){
            data?.let{
                it.data?.path?.let {
                        it1 -> viewModel.changeImage(it1)
                }
                binding.imgAvatar.setImageURI(it.data)
            }
        }
    }
}
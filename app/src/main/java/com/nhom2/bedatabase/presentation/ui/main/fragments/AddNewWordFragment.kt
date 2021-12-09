package com.nhom2.bedatabase.presentation.ui.main.fragments

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.data.util.Utils
import com.nhom2.bedatabase.databinding.FragmentAddNewWordBinding
import com.nhom2.bedatabase.domain.common.Constants
import com.nhom2.bedatabase.domain.common.Constants.ADD_ACTION
import com.nhom2.bedatabase.domain.models.Eng
import com.nhom2.bedatabase.domain.models.Vn
import com.nhom2.bedatabase.presentation.ui.main.LoadingScreen
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel

class AddNewWordFragment : Fragment() {

    private lateinit var binding: FragmentAddNewWordBinding

    private val viewModel by activityViewModels<MainViewModel>()

    private val loadingScreen by lazy{ LoadingScreen() }

    private var current_group_id: Int? = null

    private var choosedGroup = false

    private lateinit var spinnerAdapter: ArrayAdapter<CharSequence>
    private var pathImg: String? = null

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
        viewModel.currentVocabulary.observe(viewLifecycleOwner){
            if(choosedGroup){
                setVocabularyInfo(it)
            }
        }
    }

    private fun setUpUi() {
        (activity as MainActivity).showAddFabButton(false)
        (activity as MainActivity).setUpViewFullScreen()
        spinnerAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.Types, R.layout.spinner_type_item)
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.spnAddNewType.adapter = spinnerAdapter

        current_group_id?.let {
            binding.tvAddNewGroupType.text = viewModel.getGroupNameById(it)
            binding.btnChooseGroup.visibility = View.INVISIBLE
        }

        binding.btnChooseGroup.setOnClickListener {
            (activity as MainActivity).navigate(R.id.action_addNewWordFragment_to_chooseGroupFragment)
            viewModel.setCurrentVocabulary(getVocabulary())
            choosedGroup = true
        }

        binding.btnConfirm.setOnClickListener {
            viewModel.postVocabulary(getVocabulary())
            (activity as MainActivity).onBackPressed()
        }
        binding.imgWord.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.REQUEST_SELECT_AVATAR)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).resetView()
    }

    private fun getVocabulary(): Eng{
        with(binding){
             return@getVocabulary  Eng(
                    eng_id = null,
                    group_id = if(current_group_id != null) current_group_id!! else -1,
                    pronunciation = edtAddNewPronunciation.text.toString(),
                    content = edtAddNewEnglishWord.text.toString(),
                    type = spnAddNewType.selectedItem.toString(),
                    vns = listOf(Vn(null, null, edtAddNewVnWord.text.toString())),
                    path_image = pathImg
             )
        }
    }

    private fun setVocabularyInfo(vocabulary: Eng){
        with(binding){
            edtAddNewEnglishWord.setText(vocabulary.content)
            edtAddNewVnWord.setText(vocabulary.vns[0].content)
            edtAddNewPronunciation.setText(vocabulary.pronunciation)
            tvAddNewGroupType.text = viewModel.getGroupNameById(vocabulary.group_id)
            binding.spnAddNewType.setSelection(spinnerAdapter.getPosition(vocabulary.type))
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.REQUEST_SELECT_AVATAR){
            data?.data?.let{
                Log.e("path image", "$it" )
                binding.imgWord.setImageURI(it)
                pathImg = Utils.bitmapToString(MediaStore.Images.Media.getBitmap(requireContext().contentResolver, it))
            }
        }
    }
}
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
import com.nhom2.bedatabase.databinding.FragmentEditWordBinding
import com.nhom2.bedatabase.domain.common.Constants
import com.nhom2.bedatabase.domain.models.Eng
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel

class EditWordFragment : Fragment() {

    private lateinit var binding: FragmentEditWordBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var spinnerAdapter: ArrayAdapter<CharSequence>
    private var pathImg: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpUi()
        observer()
    }

    private fun observer() {
        viewModel.currentVocabulary.observe(viewLifecycleOwner){
            setVocabularyInfo(it)
        }
        viewModel.user.observe(viewLifecycleOwner){
            pathImg = it.path_image
        }
    }

    private fun setUpUi() {
        (activity as MainActivity).setUpViewFullScreen()
        spinnerAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.Types, R.layout.spinner_type_item)
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding.spnType.adapter = spinnerAdapter

        binding.btnChooseGroup.setOnClickListener {
            (activity as MainActivity).navigate(R.id.action_editWordFragment_to_chooseGroupFragment)
        }

        binding.toolbarEdit.setNavigationOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        binding.btnConfirm.setOnClickListener {
            viewModel.updateVocabulary(
                type = binding.spnType.selectedItem.toString(),
                content = binding.edtEnglishWord.text.toString(),
                vns = binding.edtVnWord.text.toString().trim(),
                pronunciation = binding.edtPronunciation.text.toString(),
                pathImg = pathImg
            )
            (activity as MainActivity).onBackPressed()
        }
        binding.imgWord.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), Constants.REQUEST_SELECT_AVATAR)
        }
        pathImg?.let{
            binding.imgWord.setImageBitmap(Utils.stringToBitmap(it))
        }
    }

    private fun setVocabularyInfo(vocabulary: Eng){
        with(binding){
            var vns = ""
            for(i in vocabulary.vns){
                vns += i.content + ", "
            }
            edtEnglishWord.setText(vocabulary.content)
            edtVnWord.setText(vns.dropLast(2))
            edtPronunciation.setText(vocabulary.pronunciation)
            tvGroupType.text = viewModel.getGroupNameById(vocabulary.group_id)
            spnType.setSelection(spinnerAdapter.getPosition(vocabulary.type))
            vocabulary.path_image?.let{
                imgWord.setImageBitmap(Utils.stringToBitmap(it))
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as MainActivity).resetView()
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
package com.nhom2.bedatabase.presentation.ui.main.fragments

import android.graphics.Color
import android.graphics.ColorFilter
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentGamePlayBinding
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.main.MainViewModel
import com.nhom2.bedatabase.presentation.ui.main.view_models.GameViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GamePlayFragment : Fragment() {
    private lateinit var binding: FragmentGamePlayBinding
    private val mainViewModel by activityViewModels<MainViewModel>()
    private val viewModel by viewModels<GameViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGamePlayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel.vocabularies.observe(viewLifecycleOwner){
            viewModel.listEngWords = it

            viewModel.getRandomWord(0)

        }
        viewModel.randomQuestions.observe(viewLifecycleOwner){
            with(binding){
                tvQuestion.text = it[viewModel.answerIndex].content
                btnAnswer1.text = it[0].vns[0].content
                btnAnswer2.text = it[1].vns[0].content
                btnAnswer3.text = it[2].vns[0].content
                btnAnswer4.text = it[3].vns[0].content
            }
        }

        with(binding){

            toolbarGame.setNavigationOnClickListener {
                (activity as MainActivity).onBackPressed()
            }

            btnAnswer1.setOnClickListener {
                viewModel.checkAnswer(0)
            }
            btnAnswer2.setOnClickListener {
                viewModel.checkAnswer(1)
            }
            btnAnswer3.setOnClickListener {
                viewModel.checkAnswer(2)
            }
            btnAnswer4.setOnClickListener {
                viewModel.checkAnswer(3)
            }
        }
        viewModel.isCorrectAnswer.observe(viewLifecycleOwner){
            if (it == 1) {
                viewModel.getRandomWord()
                binding.tvResult.visibility = View.VISIBLE
                binding.tvResult.text = getString(R.string.right_answer)
                binding.tvResult.setTextColor(resources.getColor(R.color.correct))
            }else {
                if (it == 2){
                    viewModel.getRandomWord()
                    binding.tvResult.visibility = View.VISIBLE
                    binding.tvResult.text = getString(R.string.wrong_answer)
                    binding.tvResult.setTextColor(resources.getColor(R.color.wrong))
                }
                else {
                    binding.tvResult.visibility = View.GONE
                }
            }
        }
    }

    override fun onDestroy() {
        (activity as MainActivity).resetView()
        super.onDestroy()
    }
}
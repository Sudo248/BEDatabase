package com.nhom2.bedatabase.presentation.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentNewGameBinding
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewGameFragment : Fragment() {
    lateinit var binding: FragmentNewGameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setUpViewFullScreen()
        binding.fabLetGo.setOnClickListener{
            if((activity as MainActivity).checkConditionToPlayGame()){
                (activity as MainActivity).navigate(R.id.action_newGameFragment_to_gamePlayFragment)
            }else{
                // TODO not enough eng to play game
                Toast.makeText(requireContext(), "Need 4 Vocabulary to play game", Toast.LENGTH_LONG).show()
                lifecycleScope.launch {
                    delay(2000)
                    (activity as MainActivity).onBackPressed()
                }

            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
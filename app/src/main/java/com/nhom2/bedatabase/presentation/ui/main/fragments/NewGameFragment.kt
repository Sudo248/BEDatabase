package com.nhom2.bedatabase.presentation.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentNewGameBinding
import com.nhom2.bedatabase.presentation.ui.main.MainActivity

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
            (activity as MainActivity).navigate(R.id.action_newGameFragment_to_gamePlayFragment)
        }
    }

    override fun onDestroy() {
        (activity as MainActivity).resetView()
        super.onDestroy()
    }
}
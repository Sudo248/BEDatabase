package com.nhom2.bedatabase.presentation.ui.main.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.FragmentHomeBinding
import com.nhom2.bedatabase.presentation.ui.main.MainActivity

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var nav: NavController
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        nav = Navigation.findNavController(binding.root)
        with(binding){
            linearLayout3.setOnClickListener{
                nav.navigate(R.id.action_homeFragment_to_vocabularyFragment)
            }
            linearLayout4.setOnClickListener{
                nav.navigate(R.id.action_homeFragment_to_groupFragment)
            }
            linearLayout5.setOnClickListener {
                (activity as MainActivity).setUpViewGame()
                nav.navigate(R.id.action_homeFragment_to_newGameFragment)
            }
            linearLayout6.setOnClickListener {
                nav.navigate(R.id.action_homeFragment_to_profileFragment)
            }
        }
    }
}
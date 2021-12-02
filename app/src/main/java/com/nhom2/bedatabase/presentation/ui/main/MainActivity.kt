package com.nhom2.bedatabase.presentation.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = (supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment).navController
    }

    fun navigate(id: Int){
        navController.navigate(id)
    }

    fun setUpViewGame(){
        binding.headerMain.visibility = View.GONE
        binding.bottomNavigation.visibility = View.GONE

    }

    fun resetView(){
        binding.bottomNavigation.visibility = View.VISIBLE
        binding.headerMain.visibility = View.VISIBLE
    }
}
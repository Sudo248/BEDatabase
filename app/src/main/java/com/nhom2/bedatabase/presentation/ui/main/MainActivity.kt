package com.nhom2.bedatabase.presentation.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.core.view.marginLeft
import androidx.core.view.marginStart
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.data.util.Utils
import com.nhom2.bedatabase.databinding.ActivityMainBinding
import com.nhom2.bedatabase.presentation.ui.sign.SignActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    private val TAG = "MainActivity"

    private var listSizeEng = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = (supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment).navController
        setUpUi()
        observer()
    }

    private fun setUpUi(){
        navController = (supportFragmentManager.findFragmentById(R.id.fcv_main) as NavHostFragment).navController

        binding.bottomNavigation.setupWithNavController(navController)
        binding.userImg.setImageBitmap(viewModel.user.value?.path_image?.let {
            Utils.stringToBitmap(
                it
            )
        })
//        binding.bottomNavigation.setOnItemSelectedListener {
//            if (it.itemId == R.id.newGameFragment || it.itemId == R.id.profileFragment)
//                setUpViewFullScreen()
//        }
    }

    private fun observer(){
        viewModel.user.observe(this){ user ->
            Log.d("path_image", "observer: ${user.path_image}")

            user.path_image?.let{
                Log.d(TAG, "observer: $it")
                if(it != "null" && it != "undefined")
                    binding.userImg.setImageBitmap(Utils.stringToBitmap(it))
            }
        }

        viewModel.vocabularies.observe(this){
            listSizeEng = it.size
            binding.tvNumVocabularyHeader.text = "${it.size}"
        }

        viewModel.groups.observe(this){
            binding.tvNumGroupHeader.text = "${it.size}"
        }
    }

    fun navigate(id: Int, bundle: Bundle? = null){
        navController.navigate(id, bundle)
    }

    fun setUpViewFullScreen(){
        binding.headerMain.visibility = View.GONE
        binding.bottomNavigation.visibility = View.GONE
        showAddFabButton(false)
    }

    fun resetView(){
        binding.bottomNavigation.visibility = View.VISIBLE
        binding.headerMain.visibility = View.VISIBLE
    }


    fun backToSignActivity(){
        startActivity(Intent(this, SignActivity::class.java))
        finish()
    }

    fun showAddFabButton(b: Boolean){
        binding.fabAddWord.visibility = if(b) View.VISIBLE else View.GONE
    }

    fun setAddOnClickListener(onClick: ()-> Unit){
        binding.fabAddWord.setOnClickListener {
            Log.d(TAG, "setUpUi: addOnClick")
            onClick()
        }
    }

    fun checkConditionToPlayGame() = listSizeEng >= 4

}
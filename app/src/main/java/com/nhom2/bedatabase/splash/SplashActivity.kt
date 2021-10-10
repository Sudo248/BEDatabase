package com.nhom2.bedatabase.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.nhom2.bedatabase.databinding.ActivitySplashBinding
import com.nhom2.bedatabase.presentation.MainActivity

class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkClickEvent()
    }

    private fun checkClickEvent() {
        binding.btnLetsGo.setOnClickListener {
            toMainActivity()
        }
    }

    private fun toMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }
}
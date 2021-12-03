package com.nhom2.bedatabase.presentation.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nhom2.bedatabase.R
import com.nhom2.bedatabase.presentation.ui.main.MainActivity
import com.nhom2.bedatabase.presentation.ui.sign.SignActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        findViewById<LinearLayout>(R.id.ln_let_go).setOnClickListener {
            toSignActivity()
        }

        findViewById<FloatingActionButton>(R.id.fab_let_go).setOnClickListener {
            toSignActivity()
        }

    }

    private fun toSignActivity(){
//        for testing home
//        startActivity(Intent(this, MainActivity::class.java))
//        finish()

        startActivity(Intent(this, SignActivity::class.java))
        finish()
    }

}
package com.srikant.textifyai


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Launch MainActivity after 1.5 seconds delay
        lifecycleScope.launch(Dispatchers.Main) {
            delay(1500) // 1.5 seconds delay
            val iHome = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(iHome)
            finish() // Finish SplashActivity so it can't be returned to
        }
    }
}

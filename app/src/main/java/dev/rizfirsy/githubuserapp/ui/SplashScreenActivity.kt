package dev.rizfirsy.githubuserapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import dev.rizfirsy.githubuserapp.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val splashIntent = Intent(
                this@SplashScreenActivity,
                MainActivity::class.java
            )
            startActivity(splashIntent)
            finish()
        }, 2000)
    }
}
package dev.rizfirsy.githubuserapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import dev.rizfirsy.githubuserapp.R
import dev.rizfirsy.githubuserapp.data.helper.SettingsPreferences
import dev.rizfirsy.githubuserapp.data.helper.ViewModelFactory
import dev.rizfirsy.githubuserapp.data.helper.dataStore

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val appPref = SettingsPreferences.getInstance(application.dataStore)

        val splashScreenViewModel = obtainViewModel(this@SplashScreenActivity, appPref)

        splashScreenViewModel.getThemeSettings().observe(this) {
            isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
        }

        Handler().postDelayed({
            val splashIntent = Intent(
                this@SplashScreenActivity,
                MainActivity::class.java
            )
            startActivity(splashIntent)
            finish()
        }, 2000)
    }

    private fun obtainViewModel(activity: AppCompatActivity, appPref: SettingsPreferences): SplashScreenViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, appPref)
        return ViewModelProvider(activity, factory)[SplashScreenViewModel::class.java]
    }
}
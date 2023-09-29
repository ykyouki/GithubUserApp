package dev.rizfirsy.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import dev.rizfirsy.githubuserapp.data.helper.SettingsPreferences

class SectionsPagerAdapter(activity: AppCompatActivity, val username: String,
                           private val appPref: SettingsPreferences) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return  UserFollowFragment(position, username, appPref)
    }
}
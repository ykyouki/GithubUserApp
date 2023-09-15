package dev.rizfirsy.githubuserapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    var username: String = ""

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment = UserFollowFragment()

        fragment.arguments = Bundle().apply {
            putInt(UserFollowFragment.ARG_POSITION, position + 1)
            putString(UserFollowFragment.ARG_USERNAME, username)
        }

        return fragment
    }
}
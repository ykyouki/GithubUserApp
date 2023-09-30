package dev.rizfirsy.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.rizfirsy.githubuserapp.R
import dev.rizfirsy.githubuserapp.data.database.FavoriteGithubUser
import dev.rizfirsy.githubuserapp.data.helper.SettingsPreferences
import dev.rizfirsy.githubuserapp.data.helper.ViewModelFactory
import dev.rizfirsy.githubuserapp.data.helper.dataStore
import dev.rizfirsy.githubuserapp.databinding.ActivityUserDetailBinding

class UserDetailActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityUserDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val appPref = SettingsPreferences.getInstance(application.dataStore)

        val userDetailViewModel = obtainViewModel(this@UserDetailActivity, appPref)

        userDetailViewModel.getThemeSettings().observe(this) {
            isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        intent.getStringExtra(EXTRA_USER_NAME)?.let { userDetailViewModel.getUserDetail(it) }

        userDetailViewModel.userDetailData.observe(this) { userData ->
            Glide.with(binding.ivUserDetailImage).load(userData.avatarUrl).into(binding.ivUserDetailImage)
            binding.tvUserDetailUsername.text = "@${userData.login}"
            binding.tvUserDetailName.text = userData?.name
            binding.tvUserDetailBio.text = userData.bio?.toString()
            binding.tvFollowers.text = "${userData.followers} Followers"
            binding.tvFollowing.text = "${userData.following} Followings"
            initAdapterAndTabLayout(userData.login, appPref)

            userDetailViewModel.getByUsername(userData.login)?.observe(this) {
                if (it == null) {
                    binding.fabAdd.setImageResource(R.drawable.baseline_favorite_border_24)

                    binding.fabAdd.setOnClickListener{
                        val user = FavoriteGithubUser(userData.login, userData.avatarUrl)
                        userDetailViewModel.addUserToFavorite(user)
                        Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    binding.fabAdd.setImageResource(R.drawable.baseline_favorite_24)

                    binding.fabAdd.setOnClickListener{
                        val user = FavoriteGithubUser(userData.login, userData.avatarUrl)
                        userDetailViewModel.removeUserToFavorite(user)
                        Toast.makeText(this, "Removed from Favorite", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        userDetailViewModel.isLoading.observe(this) {
            showLoading(it)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun initAdapterAndTabLayout(username: String, appPref: SettingsPreferences) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username, appPref)
        val viewPager: ViewPager2 = binding.userDetailViewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.userDetailTabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun obtainViewModel(activity: AppCompatActivity, appPref: SettingsPreferences): UserDetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application, appPref)
        return ViewModelProvider(activity, factory)[UserDetailViewModel::class.java]
    }

    companion object{
        var EXTRA_USER_NAME = "extra_user_name"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
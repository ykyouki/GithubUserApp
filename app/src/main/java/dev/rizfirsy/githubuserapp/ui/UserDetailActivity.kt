package dev.rizfirsy.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.init
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dev.rizfirsy.githubuserapp.R
import dev.rizfirsy.githubuserapp.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity() {

    companion object{
        val EXTRA_USER_DATA = "extra_user_data"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    private lateinit var binding: ActivityUserDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailViewModel::class.java)
        intent.getStringExtra(EXTRA_USER_DATA)?.let { userDetailViewModel.getUserDetail(it) }

        userDetailViewModel.userDetailData.observe(this) { userData ->
            Glide.with(binding.ivUserDetailImage).load(userData.avatarUrl).into(binding.ivUserDetailImage)
            binding.tvUserDetailUsername.text = "@${userData.login}"
            binding.tvUserDetailName.text = userData.name ?: "Pengguna ini belum memasukkan nama"
            binding.tvUserDetailBio.text = (userData.bio ?: "").toString()
            binding.tvFollowers.text = "${userData.followers ?: 0} Followers"
            binding.tvFollowing.text = "${userData.following ?: 0} Followings"
            initAdapterAndTabLayout(userData.login)
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
    private fun initAdapterAndTabLayout(username: String) {
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = binding.userDetailViewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.userDetailTabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }
}
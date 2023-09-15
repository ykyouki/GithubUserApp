package dev.rizfirsy.githubuserapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
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

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.userDetailViewPager
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = binding.userDetailTabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.hide()

        val userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailViewModel::class.java)
        intent.getStringExtra(EXTRA_USER_DATA)?.let { userDetailViewModel.getUserDetail(it) }

        userDetailViewModel.userDetailData.observe(this) { userData ->
            Glide.with(binding.ivUserDetailImage).load(userData.avatarUrl).into(binding.ivUserDetailImage)
            binding.tvUserDetailLocation.text = (userData.location)
            binding.tvUserDetailName.text = userData.name
            binding.tvUserDetailBio.text = (userData.bio).toString()
        }
    }
}
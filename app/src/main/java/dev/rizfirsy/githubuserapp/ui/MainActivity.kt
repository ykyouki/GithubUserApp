package dev.rizfirsy.githubuserapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dev.rizfirsy.githubuserapp.R
import dev.rizfirsy.githubuserapp.data.helper.ViewModelFactory
import dev.rizfirsy.githubuserapp.data.response.ItemsItem
import dev.rizfirsy.githubuserapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val layoutManager = LinearLayoutManager(this)
        binding.rvGithubUser.layoutManager =layoutManager

        val mainViewModel = obtainViewModel(this@MainActivity)
        mainViewModel.listGithubUser.observe(this) { items ->
            setGithubUserData(items)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchBar.inflateMenu(R.menu.app_menu)
            searchBar.setOnMenuItemClickListener{ item ->
                when(item.itemId) {
                    R.id.favorite_action -> {
                        val intent = Intent(this@MainActivity, FavoriteGithubUserActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.settings_action -> {
                        val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }

            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener{textView, actionId, event ->
                    searchBar.text = searchView.text
                    searchView.hide()
                    mainViewModel.searchGithubUsers(searchView.text.toString())
                    false
                }
        }
    }

    private fun setGithubUserData(githubUsers: List<ItemsItem>) {
        val adapter = GithubAdapter(githubUsers)
        binding.rvGithubUser.adapter = adapter

        adapter.setOnItemClickCallback(object : GithubAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showSelectedUser(data)
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showSelectedUser (user: ItemsItem) {
        val moveToDetailScreen = Intent(this@MainActivity, UserDetailActivity::class.java)
        moveToDetailScreen.putExtra(UserDetailActivity.EXTRA_USER_NAME, user.login)
        startActivity(moveToDetailScreen)
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[MainViewModel::class.java]
    }
}
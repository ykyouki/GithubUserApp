package dev.rizfirsy.githubuserapp.ui

import android.opengl.Visibility
import android.os.Bundle
import android.text.style.TtsSpan.ARG_USERNAME
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import dev.rizfirsy.githubuserapp.R
import dev.rizfirsy.githubuserapp.data.response.ItemsItem
import dev.rizfirsy.githubuserapp.databinding.FragmentUserFollowBinding

class UserFollowFragment(var position: Int, val username: String) : Fragment() {

    var _binding: FragmentUserFollowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvUserFollow.layoutManager = layoutManager

        val userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailViewModel::class.java)

        userDetailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if(position == 1 ) {
            userDetailViewModel.getUserFollowing(username)
            userDetailViewModel.listFollowing.observe(viewLifecycleOwner) {
                    items ->
                run {
                    if (items.size > 0) {
                        binding.tvFollowCount.visibility = View.GONE
                        setFollowingData(items)
                    } else {
                        binding.tvFollowCount.text = "0 Following"
                    }
                }
            }
        } else {
            userDetailViewModel.getUserFollowers(username)
            userDetailViewModel.listFollowers.observe(viewLifecycleOwner) {
                items -> run {
                    if (items.size > 0) {
                        binding.tvFollowCount.visibility = View.GONE
                        setFollowerData(items)
                    } else {
                        binding.tvFollowCount.text = "0 Followers"
                    }
                 }
            }
        }
    }

    private fun setFollowerData(items: List<ItemsItem>) {
            val adapter = GithubAdapter(items)
            binding.rvUserFollow.adapter = adapter
    }

    private fun setFollowingData(items: List<ItemsItem>) {
        val adapter = GithubAdapter(items)
        binding.rvUserFollow.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}
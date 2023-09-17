package dev.rizfirsy.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import dev.rizfirsy.githubuserapp.R
import dev.rizfirsy.githubuserapp.data.response.ItemsItem
import dev.rizfirsy.githubuserapp.databinding.FragmentUserFollowBinding

class UserFollowFragment : Fragment() {

    var _binding: FragmentUserFollowBinding? = null
    private val binding get() = _binding!!

    companion object {
        var ARG_POSITION: String? = null
        var ARG_USERNAME: String? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME)
        val position = arguments?.getInt(ARG_POSITION)

        val userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailViewModel::class.java)
        if (username != null) {
            userDetailViewModel.getUserFollowers(username)
        }

        userDetailViewModel.listFollowers.observe(viewLifecycleOwner) {
            items -> setFollowerData(items)
        }

        userDetailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if(position == 1 ) {
            binding.testUsername.text = "Get followers $username"
        } else {
            binding.testUsername.text = "Get following $username"
        }

    }

    private fun setFollowerData(items: List<ItemsItem>) {
            val adapter = GithubAdapter(items)
            binding.rvUserFollowers.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}
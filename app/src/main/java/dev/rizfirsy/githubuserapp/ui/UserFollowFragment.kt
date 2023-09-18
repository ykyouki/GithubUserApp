package dev.rizfirsy.githubuserapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
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
        val position = arguments?.getInt(ARG_POSITION, 0)

        val layoutManager = GridLayoutManager(requireActivity(), 2)
        binding.rvUserFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding.rvUserFollow.addItemDecoration(itemDecoration)

        val userDetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(UserDetailViewModel::class.java)


        userDetailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if(position == 1 ) {
            if (username != null) {
            userDetailViewModel.getUserFollowing(username)
            }
            userDetailViewModel.listFollowing.observe(viewLifecycleOwner) {
                    items -> setFollowingData(items)
            }
        } else {
            if (username != null) {
            userDetailViewModel.getUserFollowers(username)
            }
            userDetailViewModel.listFollowers.observe(viewLifecycleOwner) {
                    items -> setFollowerData(items)
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
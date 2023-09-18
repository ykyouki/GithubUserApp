package dev.rizfirsy.githubuserapp.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.rizfirsy.githubuserapp.data.response.GithubResponse
import dev.rizfirsy.githubuserapp.data.response.ItemsItem
import dev.rizfirsy.githubuserapp.data.response.UserDetailResponse
import dev.rizfirsy.githubuserapp.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class UserDetailViewModel: ViewModel() {
    companion object{
        val USERNAME = "username"
        val TAG = "UserDetailViewModel"
    }

    private val _isLoading = MutableLiveData<Boolean>()
    var isLoading = _isLoading

    private val _userDetailData = MutableLiveData<UserDetailResponse>()
    var userDetailData = _userDetailData

    private val _listFollowers = MutableLiveData<List<ItemsItem>>()
    var listFollowers = _listFollowers

    init {
        getUserDetail("rizky")
    }

    fun getUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserDetail(username)
        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if(response.isSuccessful) {
                    _isLoading.value = false
                    _userDetailData.value = response.body()!!
                    Log.d(TAG, response.body().toString())
                } else {
                    Log.i(TAG, "onFailure: ${response}")
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getUserFollowers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserFollowers(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                if(response.isSuccessful) {
                    _isLoading.value = false
                    _listFollowers.value = response.body()!!
                } else {
                    Log.i(TAG, "onResponse failure: ${response}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
package dev.rizfirsy.githubuserapp.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.rizfirsy.githubuserapp.data.response.GithubResponse
import dev.rizfirsy.githubuserapp.data.response.ItemsItem
import dev.rizfirsy.githubuserapp.data.retrofit.ApiConfig
import dev.rizfirsy.githubuserapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/* TODO "user saved" feature
    * #1 create user bookmark activity or fragment
    * #2 setup the ViewModel
    * #3 use database on it
    * #4 implement insert
    * #5 CRUD
 */

class MainViewModel(private val application: Application) : ViewModel() {

    var GITHUB_USERNAME: String? = "Rizky"
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _listGithubUser = MutableLiveData<List<ItemsItem>>()
    val listGithubUser: LiveData<List<ItemsItem>> = _listGithubUser

    companion object{
        val TAG = "MainViewModel"
    }

    init {
        GITHUB_USERNAME?.let { searchGithubUsers(it) }
       }
    fun searchGithubUsers(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().searchGithubUser(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if(response.isSuccessful) {
                        _listGithubUser.value = response.body()?.items
                } else {
                    Log.i(TAG, "onFailure: $response")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}
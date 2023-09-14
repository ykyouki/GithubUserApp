package dev.rizfirsy.githubuserapp.data.retrofit

import dev.rizfirsy.githubuserapp.data.response.GithubResponse
import dev.rizfirsy.githubuserapp.data.response.ItemsItem
import dev.rizfirsy.githubuserapp.data.response.UserDetailResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun searchGithubUser(@Query("q") q: String) : Call<GithubResponse>

    @GET("users/{username}")
    fun getUserDetail(@Path("username") username: String): Call<UserDetailResponse>
}
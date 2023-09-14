package dev.rizfirsy.githubuserapp.data.retrofit

import dev.rizfirsy.githubuserapp.data.response.GithubResponse
import dev.rizfirsy.githubuserapp.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: Token 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")
    fun searchGithubUser(@Query("q") q: String) : Call<GithubResponse>

    @GET("users/{username}")
    @Headers("Authorization: Token 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")
    fun findAllGithubUser(@Path("username") username: String): Call<List<ItemsItem>>
}
package br.com.mateus.githubrepositories.api

import br.com.mateus.githubrepositories.domain.JsonRepository
import br.com.mateus.githubrepositories.domain.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {

    @GET("/search/repositories")
    fun getRepositoryList(
        @Query("page") page: Int,
        @Query("q") language: String = "language:kotlin",
        @Query("sort") sort: String = "stars"
    ): Call<JsonRepository>

}
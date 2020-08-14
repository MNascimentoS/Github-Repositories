package br.com.mateus.githubrepositories.di

import br.com.mateus.githubrepositories.domain.JsonRepository
import br.com.mateus.githubrepositories.domain.Repository
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {

    @GET("/search/repositories")
    fun getRepositoryList(
        @Query("page") page: Int,
        @Query("per_page") loadSize: Int,
        @Query("q") language: String = "language:kotlin",
        @Query("sort") sort: String = "stars"
    ): Call<JsonRepository>

    @GET("/search/repositories")
    suspend fun getRepositories(
        @Query("page") page: Int,
        @Query("per_page") loadSize: Int,
        @Query("q") language: String = "language:kotlin",
        @Query("sort") sort: String = "stars"
    ): JsonRepository

}
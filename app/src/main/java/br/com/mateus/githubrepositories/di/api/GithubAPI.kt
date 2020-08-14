package br.com.mateus.githubrepositories.di.api

import br.com.mateus.githubrepositories.domain.JsonRepository
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubAPI {

    @GET("/search/repositories")
    suspend fun getRepositories(
        @Query("page") page: Int,
        @Query("per_page") loadSize: Int,
        @Query("q") language: String = "language:kotlin",
        @Query("sort") sort: String = "stars"
    ): JsonRepository

}
package br.com.mateus.githubrepositories.api

import br.com.mateus.githubrepositories.domain.Repository
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitConfig {

    private var gson = GsonBuilder().registerTypeAdapter(Repository::class.java, RepositoryDes()).create()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun getRepositoryAPI(): GithubAPI? = retrofit.create(GithubAPI::class.java)

    companion object {
        private const val BASE_URL = "https://api.github.com/"
    }
}
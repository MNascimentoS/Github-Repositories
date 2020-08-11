package br.com.mateus.githubrepositories.dataSource

import android.util.Log
import androidx.paging.PageKeyedDataSource
import br.com.mateus.githubrepositories.api.RetrofitConfig
import br.com.mateus.githubrepositories.domain.JsonRepository
import br.com.mateus.githubrepositories.domain.Repository
import br.com.mateus.githubrepositories.utils.toRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDataSource: PageKeyedDataSource<Int, Repository>(), KoinComponent {

    private val retrofitConfig by inject<RetrofitConfig>()
    private var page = 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repository>
    ) {
        retrofitConfig.getRepositoryAPI()?.getRepositoryList(page)?.let { call ->
            call.enqueue(object :
                Callback<JsonRepository> {
                override fun onFailure(call: Call<JsonRepository>, t: Throwable) {

                }

                override fun onResponse(call: Call<JsonRepository>, response: Response<JsonRepository>) {
                    page++
                    val repositories = response.body()!!.items.map { it.toRepository() }
                    callback.onResult(repositories, null, page)
                }

            })
        } ?: run {

        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        retrofitConfig.getRepositoryAPI()?.getRepositoryList(page)?.let { call ->
            call.enqueue(object :
                Callback<JsonRepository> {
                override fun onFailure(call: Call<JsonRepository>, t: Throwable) {

                }

                override fun onResponse(call: Call<JsonRepository>, response: Response<JsonRepository>) {
                    page++
                    val repositories = response.body()!!.items.map { it.toRepository() }
                    callback.onResult(repositories, page)
                }

            })
        } ?: run {

        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {}

}
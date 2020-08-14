package br.com.mateus.githubrepositories.di.dataSource

import androidx.paging.PageKeyedDataSource
import br.com.mateus.githubrepositories.core.CoroutineContextProvider
import br.com.mateus.githubrepositories.di.repository.GitRepository
import br.com.mateus.githubrepositories.domain.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class RepositoryDataSource: PageKeyedDataSource<Int, Repository>(), KoinComponent {

    private val gitRepository by inject<GitRepository>()
    private val contextProvider by inject<CoroutineContextProvider>()
    private val scope = CoroutineScope(Job() + contextProvider.io)

    private var page = 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Repository>
    ) {
        scope.launch {
            try {
                val repositories = gitRepository.getGitRepositoryList(page, params.requestedLoadSize)
                page++
                callback.onResult(repositories, null, page)
            } catch (ex: Exception) {
                callback.onResult(arrayListOf(), null, null)
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {
        scope.launch {
            try {
                val repositories = gitRepository.getGitRepositoryList(page, params.requestedLoadSize)
                page++
                callback.onResult(repositories, page)
            } catch (ex: Exception) {
                callback.onResult(arrayListOf(), page)
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Repository>) {}

}
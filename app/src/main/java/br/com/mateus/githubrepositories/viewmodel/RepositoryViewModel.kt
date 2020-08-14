package br.com.mateus.githubrepositories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.mateus.githubrepositories.di.dataSource.RepositoryDataSourceFactory
import br.com.mateus.githubrepositories.domain.Repository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.Executors

class RepositoryViewModel : ViewModel(), KoinComponent {

    val repositoryDataSourceFactory by inject<RepositoryDataSourceFactory>()

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _hasItems = MutableLiveData<Boolean>()
    val hasItems: LiveData<Boolean> = _hasItems

    var repositoryList =
        LivePagedListBuilder<Int, Repository>(repositoryDataSourceFactory, PAGE_SIZE)
            .setFetchExecutor(Executors.newFixedThreadPool(EXECUTOR_THREAD_SIZE))
            .setBoundaryCallback(object : PagedList.BoundaryCallback<Repository>() {
                override fun onZeroItemsLoaded() {
                    super.onZeroItemsLoaded()
                    _isLoading.value = false
                    _hasItems.value = false
                }

                override fun onItemAtEndLoaded(itemAtEnd: Repository) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    _isLoading.value = false
                    _hasItems.value = true
                }

                override fun onItemAtFrontLoaded(itemAtFront: Repository) {
                    super.onItemAtFrontLoaded(itemAtFront)
                    _isLoading.value = false
                    _hasItems.value = true
                }
            })
            .build()

    fun refreshList() {
        repositoryList.value?.dataSource?.invalidate()
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val EXECUTOR_THREAD_SIZE = 5
    }


}
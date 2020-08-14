package br.com.mateus.githubrepositories.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.mateus.githubrepositories.domain.Repository
import br.com.mateus.githubrepositories.di.dataSource.RepositoryDataSourceFactory
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.Executors

class RepositoryViewModel : ViewModel(), KoinComponent {

    private val repositoryDataSourceFactory by inject<RepositoryDataSourceFactory>()

    var repositoryList: LiveData<PagedList<Repository>>

    var selectedRepository: Repository? = null

    private val _isLoading = MutableLiveData<Boolean>()
    private val isLoading: LiveData<Boolean> = _isLoading

    init {
        val executor = Executors.newFixedThreadPool(EXECUTOR_THREAD_SIZE)

        repositoryDataSourceFactory.mutableLiveData?.observeForever { dataSource ->
//            dataSource.loading.observeForever {
//                _isLoading.postValue(it)
//            }
        }

        val pagedListConfig = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(PAGE_SIZE)
            .setPageSize(PAGE_SIZE).build()

        repositoryList = LivePagedListBuilder<Int, Repository>(repositoryDataSourceFactory, pagedListConfig)
            .setFetchExecutor(executor)
            .setBoundaryCallback(object : PagedList.BoundaryCallback<Repository>() {
                override fun onZeroItemsLoaded() {
                    super.onZeroItemsLoaded()
                    _isLoading.value = false
//                    _hasItems.value = false
                }

                override fun onItemAtEndLoaded(itemAtEnd: Repository) {
                    super.onItemAtEndLoaded(itemAtEnd)
                    _isLoading.value = false
//                    _hasItems.value = true
                }

                override fun onItemAtFrontLoaded(itemAtFront: Repository) {
                    super.onItemAtFrontLoaded(itemAtFront)
                    _isLoading.value = false
//                    _hasItems.value = true
                }
            })
            .build()
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val EXECUTOR_THREAD_SIZE = 5
    }


}
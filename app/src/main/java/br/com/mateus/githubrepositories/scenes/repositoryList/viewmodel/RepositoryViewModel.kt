package br.com.mateus.githubrepositories.scenes.repositoryList.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import br.com.mateus.githubrepositories.domain.Repository
import br.com.mateus.githubrepositories.dataSource.RepositoryDataSourceFactory
import org.koin.core.KoinComponent
import org.koin.core.inject

class RepositoryViewModel : ViewModel(), KoinComponent {

    private val repositoryDataSourceFactory by inject<RepositoryDataSourceFactory>()

    var repositoryList: LiveData<PagedList<Repository>> =
        LivePagedListBuilder<Int, Repository>(repositoryDataSourceFactory, 10)
            .setBoundaryCallback(object : PagedList.BoundaryCallback<Repository>() {
                override fun onZeroItemsLoaded() {
                    super.onZeroItemsLoaded()
//                    isLoading.value = false
//                    hasItems.value = false
                }

                override fun onItemAtEndLoaded(itemAtEnd: Repository) {
                    super.onItemAtEndLoaded(itemAtEnd)
//                    hasItems.value = true
//                    isLoading.value = false
                }

                override fun onItemAtFrontLoaded(itemAtFront: Repository) {
                    super.onItemAtFrontLoaded(itemAtFront)
//                    hasItems.value = true
//                    isLoading.value = false
                }
            })
            .build()


}
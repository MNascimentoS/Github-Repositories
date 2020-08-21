package br.com.mateus.githubrepositories.di.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.mateus.githubrepositories.domain.Repository

class RepositoryDataSourceFactory : DataSource.Factory<Int, Repository>() {

    private var repositoryDataSource: RepositoryDataSource? = null
    private var mutableLiveData: MutableLiveData<RepositoryDataSource>? = null
    private var search: String? = null

    init {
        mutableLiveData = MutableLiveData()
    }

    override fun create(): DataSource<Int, Repository> {
        repositoryDataSource = RepositoryDataSource(search)
        mutableLiveData?.postValue(repositoryDataSource)
        return repositoryDataSource as RepositoryDataSource
    }

    fun searchRepository(search: String?) {
        this.search = search
    }

}
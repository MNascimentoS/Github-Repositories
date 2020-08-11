package br.com.mateus.githubrepositories.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import br.com.mateus.githubrepositories.domain.Repository

class RepositoryDataSourceFactory : DataSource.Factory<Int, Repository>() {

    private var repositoryDataSource: RepositoryDataSource? = null
    private var mutableLiveData: MutableLiveData<RepositoryDataSource>? = null

    init {
        mutableLiveData = MutableLiveData()
    }

    override fun create(): DataSource<Int, Repository> {
        repositoryDataSource = RepositoryDataSource()
        mutableLiveData?.postValue(repositoryDataSource)
        return repositoryDataSource as RepositoryDataSource
    }

}
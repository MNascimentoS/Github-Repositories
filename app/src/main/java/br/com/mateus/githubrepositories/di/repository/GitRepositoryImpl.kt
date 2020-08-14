package br.com.mateus.githubrepositories.di.repository

import br.com.mateus.githubrepositories.di.api.RetrofitConfig
import br.com.mateus.githubrepositories.domain.Repository
import br.com.mateus.githubrepositories.utils.toRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception

class GitRepositoryImpl : GitRepository, KoinComponent  {

    private val retrofitConfig by inject<RetrofitConfig>()

    override suspend fun getGitRepositoryList(pagePos: Int, pageSize: Int) : List<Repository> {
        val jsonRepository = retrofitConfig.getRepositoryAPI()?.getRepositories(pagePos, pageSize)
        val repositoryList = jsonRepository?.items?.map { it.toRepository() }
        repositoryList?.let { return it } ?: run { throw Exception() }
    }

}
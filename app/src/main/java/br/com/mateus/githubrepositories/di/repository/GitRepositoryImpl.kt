package br.com.mateus.githubrepositories.di.repository

import br.com.mateus.githubrepositories.di.RetrofitConfig
import br.com.mateus.githubrepositories.domain.Repository
import br.com.mateus.githubrepositories.utils.toRepository
import org.koin.core.KoinComponent
import org.koin.core.inject

class GitRepositoryImpl : GitRepository, KoinComponent  {

    private val retrofitConfig by inject<RetrofitConfig>()

    override suspend fun getGitRepositoryList(pagePos: Int, pageSize: Int) : List<Repository> {
        val jsonRepository = retrofitConfig.getRepositoryAPI()!!.getRepositories(pagePos, pageSize)
        return jsonRepository.items.map { it.toRepository() }
    }

}
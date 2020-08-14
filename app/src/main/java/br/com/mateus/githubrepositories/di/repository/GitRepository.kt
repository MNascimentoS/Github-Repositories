package br.com.mateus.githubrepositories.di.repository

import br.com.mateus.githubrepositories.domain.Repository

interface GitRepository {

    suspend fun getGitRepositoryList(pagePos: Int, pageSize: Int) : List<Repository>

}
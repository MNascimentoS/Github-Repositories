package br.com.mateus.githubrepositories.core

import android.app.Application
import br.com.mateus.githubrepositories.di.RetrofitConfig
import br.com.mateus.githubrepositories.di.dataSource.RepositoryDataSourceFactory
import br.com.mateus.githubrepositories.di.repository.GitRepository
import br.com.mateus.githubrepositories.di.repository.GitRepositoryImpl
import br.com.mateus.githubrepositories.viewmodel.RepositoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(
                module {
                    single { CoroutineContextProvider() }
                    single { RepositoryDataSourceFactory() }
                    single { RetrofitConfig() }
                    single<GitRepository> { GitRepositoryImpl() }

                    viewModel { RepositoryViewModel() }
                }
            ))
        }
    }
}

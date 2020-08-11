package br.com.mateus.githubrepositories.core

import android.app.Application
import br.com.mateus.githubrepositories.api.RetrofitConfig
import br.com.mateus.githubrepositories.dataSource.RepositoryDataSourceFactory
import br.com.mateus.githubrepositories.scenes.repositoryList.viewmodel.RepositoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(listOf(
                module {
                    single {
                        RepositoryDataSourceFactory()
                    }

                    single {
                        RetrofitConfig()
                    }

                    viewModel {
                        RepositoryViewModel()
                    }
                }
            ))
        }
    }
}

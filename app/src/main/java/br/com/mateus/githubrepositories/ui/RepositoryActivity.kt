package br.com.mateus.githubrepositories.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.mateus.githubrepositories.BuildConfig
import br.com.mateus.githubrepositories.R
import br.com.mateus.githubrepositories.utils.gone
import br.com.mateus.githubrepositories.utils.observe
import br.com.mateus.githubrepositories.utils.visible
import br.com.mateus.githubrepositories.viewmodel.RepositoryViewModel
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.distribute.Distribute
import kotlinx.android.synthetic.main.activity_repository.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class RepositoryActivity : AppCompatActivity() {

    private val repositoryViewModel by viewModel<RepositoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)
        if (!BuildConfig.DEBUG) {
            AppCenter.start(
                application, BuildConfig.APPCENTER_KEY,
                Analytics::class.java, Crashes::class.java, Distribute::class.java
            )
        }
        initObservers()
    }

    private fun initObservers() {
        observe(repositoryViewModel.isLoading) { loading ->
            if (loading) progressBar?.visible()
            else progressBar?.gone()
        }
    }

    companion object {
        const val CARD_TRANSITION = "card"
    }

}
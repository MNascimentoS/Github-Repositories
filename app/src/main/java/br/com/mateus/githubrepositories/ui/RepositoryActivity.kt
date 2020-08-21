package br.com.mateus.githubrepositories.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
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
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)
        AppCenter.start(
            application, BuildConfig.APPCENTER_KEY,
            Analytics::class.java, Crashes::class.java, Distribute::class.java
        )
        initUi()
        initObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_default, menu)
        (menu.findItem(R.id.search).actionView as SearchView).apply {
            queryHint = getString(R.string.search_hint)

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(search: String?): Boolean {
                    navController.navigate(
                        RepositoryListFragmentDirections.actionRepositoryListFragmentSelf(
                            search
                        )
                    )
                    return true
                }

                override fun onQueryTextChange(search: String?): Boolean {
                    if (search.isNullOrBlank()) navController.navigate(
                        RepositoryListFragmentDirections.actionRepositoryListFragmentSelf(null)
                    )
                    return true
                }
            })
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun initUi() {
        setSupportActionBar(toolbar)
        navController = findNavController(R.id.navHostFG)
        appBarConfiguration = AppBarConfiguration(navController.graph)

        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    private fun initObservers() {
        observe(repositoryViewModel.isLoading) { loading ->
            if (loading) progressBar?.visible()
            else progressBar?.gone()
        }
        observe(repositoryViewModel.hasItems) { hasItem ->
            if (hasItem) errorMessageTXT?.gone()
            else errorMessageTXT?.visible()
        }
    }

    companion object {
        const val CARD_TRANSITION_START = "card_start"
        const val CARD_TRANSITION_END = "card_end"
    }

}
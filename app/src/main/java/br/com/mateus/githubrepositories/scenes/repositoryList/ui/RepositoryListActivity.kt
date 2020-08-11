package br.com.mateus.githubrepositories.scenes.repositoryList.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mateus.githubrepositories.R
import br.com.mateus.githubrepositories.scenes.repositoryList.viewmodel.RepositoryViewModel
import br.com.mateus.githubrepositories.utils.observe
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryListActivity : AppCompatActivity() {

    private val repositoryViewModel by viewModel<RepositoryViewModel>()
    private val repositoryRecyclerAdapter by lazy { RepositoryRecyclerAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
        initObserver()
    }

    private fun initUi() {
        repositoryRCL?.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        repositoryRCL?.adapter = repositoryRecyclerAdapter
    }

    private fun initObserver() {
        observe(repositoryViewModel.repositoryList) {
            repositoryRecyclerAdapter.submitList(it)
        }
    }
}
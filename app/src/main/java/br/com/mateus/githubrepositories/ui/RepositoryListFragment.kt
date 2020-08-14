package br.com.mateus.githubrepositories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mateus.githubrepositories.R
import br.com.mateus.githubrepositories.utils.observe
import br.com.mateus.githubrepositories.viewmodel.RepositoryViewModel
import kotlinx.android.synthetic.main.fragment_repository_list.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RepositoryListFragment : Fragment() {

    private val repositoryViewModel by sharedViewModel<RepositoryViewModel>()
    private lateinit var repositoryRecyclerAdapter: RepositoryRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_repository_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initObserver()
    }

    private fun initUi() {
        repositoryRecyclerAdapter = RepositoryRecyclerAdapter { repository ->
            findNavController(repositoryRCL).navigate(RepositoryListFragmentDirections.actionRepositoryListFragmentToRepositoryDetailsFragment(repository))
        }

        repositoryRCL?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        repositoryRCL?.adapter = repositoryRecyclerAdapter
    }

    private fun initObserver() {
        observe(repositoryViewModel.repositoryList) {
            repositoryRecyclerAdapter.submitList(it)
        }
    }

}
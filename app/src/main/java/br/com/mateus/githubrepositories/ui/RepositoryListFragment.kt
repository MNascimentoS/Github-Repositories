package br.com.mateus.githubrepositories.ui

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mateus.githubrepositories.R
import br.com.mateus.githubrepositories.ui.RepositoryActivity.Companion.CARD_TRANSITION_END
import br.com.mateus.githubrepositories.ui.RepositoryActivity.Companion.CARD_TRANSITION_START
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

        arguments?.let {
            val search = RepositoryListFragmentArgs.fromBundle(arguments!!).searchValue
            if (!search.isNullOrBlank()) {
                repositoryViewModel.searchRepository(search)
            } else {
                repositoryViewModel.searchRepository(null)
            }
        }
        initUi()
        initObserver()
    }

    private fun initUi() {
        repositoryRecyclerAdapter = RepositoryRecyclerAdapter { repository, view ->
            view.transitionName = CARD_TRANSITION_START
            val extras = FragmentNavigatorExtras(
                view to CARD_TRANSITION_END
            )
            findNavController(repositoryRCL)
                .navigate(
                    RepositoryListFragmentDirections.actionRepositoryListFragmentToRepositoryDetailsFragment(
                        repository
                    ),
                    extras
                )
        }

        repositoryRCL?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        repositoryRCL?.adapter = repositoryRecyclerAdapter

        pullToRefresh?.setOnRefreshListener {
            repositoryViewModel.refreshList()
        }
    }

    private fun initObserver() {
        observe(repositoryViewModel.hasItems) {
            pullToRefresh?.isRefreshing = false
        }
        observe(repositoryViewModel.repositoryList) {
            pullToRefresh?.isRefreshing = false
            repositoryRecyclerAdapter.submitList(it)
        }
    }

}
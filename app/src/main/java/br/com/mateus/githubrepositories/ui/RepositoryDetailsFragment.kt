package br.com.mateus.githubrepositories.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.mateus.githubrepositories.R
import br.com.mateus.githubrepositories.domain.Repository
import br.com.mateus.githubrepositories.ui.RepositoryDetailsFragmentArgs.Companion.fromBundle
import br.com.mateus.githubrepositories.viewmodel.RepositoryViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RepositoryDetailsFragment : Fragment() {

    private val repositoryViewModel by sharedViewModel<RepositoryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_repository_details, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository: Repository
        arguments?.let {
            repository = fromBundle(arguments!!).repository
        } ?: run {
            findNavController().navigateUp()
        }
    }

}
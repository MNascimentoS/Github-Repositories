package br.com.mateus.githubrepositories.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.mateus.githubrepositories.R
import br.com.mateus.githubrepositories.domain.Repository
import br.com.mateus.githubrepositories.ui.RepositoryDetailsFragmentArgs.Companion.fromBundle
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_repository_details.*


class RepositoryDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedElementEnterTransition = TransitionInflater.from(this.context).inflateTransition(R.transition.transaction)
        sharedElementReturnTransition =  TransitionInflater.from(this.context).inflateTransition(R.transition.transaction)

        return inflater.inflate(R.layout.fragment_repository_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val repository: Repository
        arguments?.let {
            repository = fromBundle(arguments!!).repository
            initUi(repository)
        } ?: run {
            findNavController().navigateUp()
        }

    }

    private fun initUi(repository: Repository) {
        repositoryNameTXT?.text = repository.name.capitalize()
        repositoryDetailsTXT?.text = repository.description

        starsTXT?.text = getString(R.string.x_amount_stars, repository.stars)
        issuesTXT?.text = getString(R.string.x_amount_stars, repository.issues)
        forkTXT?.text = getString(R.string.x_amount_stars, repository.forks)
        watchersTXT?.text = getString(R.string.x_amount_stars, repository.watchers)

        nameTXT?.text = repository.ownerName
        descriptionTXT?.text = repository.ownerType
        Picasso.get().load(repository.imageUrl).into(avatarIMG)

        openRepositoryBTN?.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(repository.repositoryUrl)))
        }
    }

}
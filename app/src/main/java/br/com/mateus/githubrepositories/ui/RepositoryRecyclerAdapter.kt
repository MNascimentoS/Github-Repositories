package br.com.mateus.githubrepositories.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.mateus.githubrepositories.R
import br.com.mateus.githubrepositories.domain.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_github_repository.view.*

class RepositoryRecyclerAdapter(private val onItemSelected: ((repository: Repository) -> Unit)) :
    PagedListAdapter<Repository, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        object : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_github_repository, parent, false)) {}

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val repositoryItem = getItem(position)
        repositoryItem?.let { repository ->
            with(holder.itemView) {
                nameTXT?.text = repository.name
                descriptionTXT?.text = repository.description
                Picasso.get().load(repository.imageUrl).into(avatarIMG)
                repositoryFL?.setOnClickListener {
                    onItemSelected(repository)
                }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(
                oldConcert: Repository,
                newConcert: Repository
            ) = (oldConcert.id == newConcert.id)

            override fun areContentsTheSame(
                oldConcert: Repository,
                newConcert: Repository
            ) = oldConcert == newConcert
        }
    }

}
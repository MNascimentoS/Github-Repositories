package br.com.mateus.githubrepositories.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mateus.githubrepositories.R
import br.com.mateus.githubrepositories.viewmodel.RepositoryViewModel
import br.com.mateus.githubrepositories.utils.observe
import kotlinx.android.synthetic.main.activity_repository.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository)
    }

    companion object {
        const val CARD_TRANSITION = "card"
    }

}
package br.com.mateus.githubrepositories.domain

data class Item(
    val id: Int,
    val description: String,
    val forks: Int,
    val html_url: String,
    val name: String,
    val open_issues: Int,
    val owner: Owner,
    val stargazers_count: Int,
    val watchers: Int
)
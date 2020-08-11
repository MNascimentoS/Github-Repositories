package br.com.mateus.githubrepositories.domain

data class JsonRepository(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)
package br.com.mateus.githubrepositories.utils

import br.com.mateus.githubrepositories.domain.Item
import br.com.mateus.githubrepositories.domain.Repository

fun Item.toRepository() =
    Repository(
        id = id,
        name = name
    )
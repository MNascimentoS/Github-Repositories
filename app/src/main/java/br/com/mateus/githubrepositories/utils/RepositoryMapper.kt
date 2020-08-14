package br.com.mateus.githubrepositories.utils

import br.com.mateus.githubrepositories.domain.Item
import br.com.mateus.githubrepositories.domain.Repository

fun Item.toRepository() =
    Repository(
        id = id,
        name = name,
        description = description,
        ownerName = owner.login,
        ownerType = owner.type,
        imageUrl = owner.avatar_url,
        repositoryUrl = html_url,
        stars = stargazers_count,
        watchers = watchers,
        forks = forks,
        issues = open_issues
    )
package br.com.mateus.githubrepositories.utils

import br.com.mateus.githubrepositories.domain.Item
import br.com.mateus.githubrepositories.domain.Owner
import org.junit.Assert
import org.junit.Test

class RepositoryMapper {

    @Test
    fun `mapper from api json to repository`() {
        //Given a bank slip from api
        val jsonItemRepository = Item(
            id = ID.toInt(),
            name = NAME,
            description = DESCRIPTION,
            html_url = REPOSITORY_URL,
            owner = Owner(
                login = OWNER_NAME,
                type = OWNER_TYPE,
                avatar_url = OWNER_AVATAR
            ),
            stargazers_count = STAR_AMOUNT.toInt(),
            open_issues = ISSUES_AMOUNT.toInt(),
            watchers = WATCHERS_AMOUNT.toInt(),
            forks = FORKS_AMOUNT.toInt()
        )
        //When mapped
        val result = jsonItemRepository.toRepository()
        //Then values need be equals
        Assert.assertEquals(jsonItemRepository.id.toDouble(), result.id.toDouble(), ID)
        Assert.assertEquals(jsonItemRepository.name, result.name, NAME)
        Assert.assertEquals(jsonItemRepository.description, result.description, DESCRIPTION)
        Assert.assertEquals(jsonItemRepository.owner.login, result.ownerName, OWNER_NAME)
        Assert.assertEquals(jsonItemRepository.owner.type, result.ownerType, OWNER_TYPE)
        Assert.assertEquals(jsonItemRepository.owner.avatar_url, result.imageUrl, OWNER_AVATAR)
        Assert.assertEquals(jsonItemRepository.html_url, result.repositoryUrl, REPOSITORY_URL)
        Assert.assertEquals(jsonItemRepository.stargazers_count.toDouble(), result.stars.toDouble(), STAR_AMOUNT)
        Assert.assertEquals(jsonItemRepository.open_issues.toDouble(), result.issues.toDouble(), ISSUES_AMOUNT)
        Assert.assertEquals(jsonItemRepository.watchers.toDouble(), result.watchers.toDouble(), WATCHERS_AMOUNT)
        Assert.assertEquals(jsonItemRepository.forks.toDouble(), result.forks.toDouble(), FORKS_AMOUNT)
    }

    companion object {
        const val ID = 0.toDouble()
        const val NAME = "name"
        const val DESCRIPTION = "description"
        const val OWNER_NAME = "description"
        const val OWNER_TYPE = "description"
        const val OWNER_AVATAR = "description"
        const val REPOSITORY_URL = "description"
        const val STAR_AMOUNT = 1.toDouble()
        const val ISSUES_AMOUNT = 2.toDouble()
        const val WATCHERS_AMOUNT = 3.toDouble()
        const val FORKS_AMOUNT = 4.toDouble()
    }

}
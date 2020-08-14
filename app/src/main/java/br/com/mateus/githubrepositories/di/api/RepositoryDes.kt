package br.com.mateus.githubrepositories.di.api

import br.com.mateus.githubrepositories.domain.Repository
import com.google.gson.*
import java.lang.reflect.Type


class RepositoryDes : JsonDeserializer<Any?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Any {
        var repository: JsonElement? = json.asJsonObject
        if (json.asJsonObject["items"] != null) {
            repository = json.asJsonObject["items"]
        }
        return Gson().fromJson(repository, Repository::class.java)
    }
}
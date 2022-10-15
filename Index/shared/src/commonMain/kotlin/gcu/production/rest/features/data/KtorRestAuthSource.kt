package gcu.production.rest.features.data

import gcu.production.rest.features.source.RestAuthSource
import gcu.production.models.user.AuthEntity
import gcu.production.models.user.UserInputEntity
import gcu.production.models.user.UserOutputEntity
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.*
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
class KtorRestAuthSource(
    private val httpClient: HttpClient
) : RestAuthSource {

    override suspend fun registration(
        userOutputEntity: UserOutputEntity
    ): Boolean = try {
        this.httpClient.post<HttpStatement>
        {
            url {
                path("registration")
                header(key = "Content-Type", value = "application/json")
                body = Json.encodeToString(userOutputEntity)
            }
        }.execute().status.isSuccess()
    } catch (ex: Exception) {
        false
    }

    override suspend fun login(
        authData: AuthEntity
    ): UserInputEntity? = try {
        this.httpClient.get<HttpStatement>
        {
            url {
                path("login")
                header(
                    HttpHeaders.Authorization,
                    value = authData.requireAuthData()
                )
            }
        }.execute().let {
            if (it.status == HttpStatusCode.OK)
                Json.decodeFromString(
                    it.readText()
                ) as UserInputEntity
            else null
        }
    } catch (ex: Exception) {
        null
    }
}
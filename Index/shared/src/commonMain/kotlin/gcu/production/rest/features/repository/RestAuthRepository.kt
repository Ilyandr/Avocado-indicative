package gcu.production.rest.features.repository

import gcu.production.rest.features.source.RestAuthSource
import gcu.production.models.user.AuthEntity
import gcu.production.models.user.UserOutputEntity

class RestAuthRepository(
    private val restAuthSource: RestAuthSource
) {
    suspend fun registration(
        userOutputEntity: UserOutputEntity
    ) =
        this.restAuthSource.registration(userOutputEntity)

    suspend fun login(
        authData: AuthEntity
    ) =
        this.restAuthSource.login(authData)
}
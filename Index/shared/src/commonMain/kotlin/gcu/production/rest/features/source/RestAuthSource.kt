package gcu.production.rest.features.source

import gcu.production.models.user.AuthEntity
import gcu.production.models.user.UserInputEntity
import gcu.production.models.user.UserOutputEntity

interface RestAuthSource {

    suspend fun registration(
        userOutputEntity: UserOutputEntity
    ): Boolean

    suspend fun login(
        authData: AuthEntity
    ): UserInputEntity?
}
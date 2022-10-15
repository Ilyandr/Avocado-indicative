package gcu.production.rest.features.source

import gcu.production.models.user.UserInputEntity

interface RestRecordSource
{
    suspend fun setRecord(
        userAuthKey: String?
        , dataToken: String?
        , userLocation: Pair<Double, Double>): Long?

    suspend fun getAllRecord(
        userAuthKey: String?, pointId: Long): MutableList<UserInputEntity>?
}
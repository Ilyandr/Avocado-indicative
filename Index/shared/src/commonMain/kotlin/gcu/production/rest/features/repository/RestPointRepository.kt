package gcu.production.rest.features.repository

import gcu.production.rest.features.source.RestPointSource
import gcu.production.qrcheck.RestAPI.Models.Point.DataPointOutputEntity

class RestPointRepository(
    private val restPointSource: RestPointSource
)
{
    suspend fun generatePoint(
        userLoginKey: String?
        , dataPointOutputEntity: DataPointOutputEntity
    ) =
        this.restPointSource.generatePoint(
            userLoginKey, dataPointOutputEntity)

    suspend fun generateToken(
        userLoginKey: String?, pointId: Long) =
        this.restPointSource.generateToken(
            userLoginKey, pointId)

    suspend fun getAllPoint(userLoginKey: String?) =
        this.restPointSource.getAllPoint(userLoginKey)
}
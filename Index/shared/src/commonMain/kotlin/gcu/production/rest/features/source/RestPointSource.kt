package gcu.production.rest.features.source

import gcu.production.qrcheck.RestAPI.Models.Point.DataPointInputEntity
import gcu.production.qrcheck.RestAPI.Models.Point.DataPointOutputEntity

interface RestPointSource
{
    suspend fun generatePoint(
        userLoginKey: String?
        , dataPointOutputEntity: DataPointOutputEntity
    ): DataPointInputEntity

    suspend fun generateToken(
        userLoginKey: String?, pointID: Long): String?

    suspend fun getAllPoint(
        userLoginKey: String?): MutableList<DataPointInputEntity>?
}
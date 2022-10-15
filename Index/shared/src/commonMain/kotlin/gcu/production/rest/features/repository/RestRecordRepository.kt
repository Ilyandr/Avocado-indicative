package gcu.production.rest.features.repository

import gcu.production.qrcheck.RestAPI.Features.RestInteraction.KtorAPI.KtorRestRecordSource

class RestRecordRepository(
    private val restRecordDataSource: KtorRestRecordSource)
{
    suspend fun setRecord(
        userAuthKey: String?
        , dataToken: String?
        , userLocation: Pair<Double, Double>) =
        this.restRecordDataSource.setRecord(
            userAuthKey, dataToken, userLocation)

    suspend fun getAllRecord(
        userAuthKey: String?, pointId: Long) =
        this.restRecordDataSource.getAllRecord(
            userAuthKey, pointId)
}
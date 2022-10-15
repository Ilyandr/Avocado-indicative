package gcu.production.rest.features.repository

import gcu.production.rest.features.source.RestOrgSource
import gcu.production.models.payment.PaymentModel
import gcu.production.models.user.AuthEntity
import gcu.production.models.user.OrgModel

class RestOrgRepository(
    private val source: RestOrgSource
) {
    suspend fun createOrganization(
        headerAuth: AuthEntity, data: OrgModel
    ) =
        source.createOrganization(
            headerAuth, data
        )

    suspend fun sendTokenizeData(
        headerAuth: String,
        uniqueOperationKey: String,
        sendPaymentDataEntity: PaymentModel
    ) =
        source.sendTokenizeData(
            headerAuth,
            uniqueOperationKey,
            sendPaymentDataEntity
        )

    suspend fun putPaymentResult(
        headerAuth: String,
        singlePaymentID: String
    ) = source.putPaymentResult(
        headerAuth, singlePaymentID
    )
}
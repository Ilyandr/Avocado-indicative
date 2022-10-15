package gcu.production.rest.features.source

import gcu.production.models.payment.PaymentModel
import gcu.production.models.user.AuthEntity
import gcu.production.models.user.OrgModel

interface RestOrgSource {

    suspend fun createOrganization(
        headerAuth: AuthEntity, data: OrgModel
    ): Boolean

    suspend fun sendTokenizeData(
        headerAuth: String,
        uniqueOperationKey: String,
        sendPaymentDataEntity: PaymentModel
    ): String?

    suspend fun putPaymentResult(
        headerAuth: String,
        singlePaymentID: String
    ): Boolean
}
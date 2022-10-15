package gcu.production.rest.features.data

import gcu.production.rest.features.source.RestOrgSource
import gcu.production.models.payment.PaymentModel
import gcu.production.models.user.AuthEntity
import gcu.production.models.user.OrgModel
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpStatement
import io.ktor.client.statement.readText
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json


@ExperimentalSerializationApi
class KtorOrgDataSource(
    private val paymentHttpClient: HttpClient,
    private val defaultHttpClient: HttpClient
) : RestOrgSource {

    override suspend fun createOrganization(
        headerAuth: AuthEntity, data: OrgModel
    ) = try {
        this.paymentHttpClient.post<HttpStatement> {
            url {
                path("createOrganization")
                header(key = "Authorization", value = headerAuth.requireAuthData())
                body = Json.encodeToString(data)
            }
        }.execute().readText().toBoolean()
    } catch (ex: Exception) {
        false
    }

    override suspend fun sendTokenizeData(
        headerAuth: String,
        uniqueOperationKey: String,
        sendPaymentDataEntity: PaymentModel
    ) = try {
        this.paymentHttpClient.post<HttpStatement> {
            url {
                path("payments")

                headers {
                    header(key = "Content-Type", value = "application/json")
                    header(key = "Authorization", value = headerAuth)
                    header(key = "Idempotence-Key", value = uniqueOperationKey)
                }

                body = Json.encodeToString(sendPaymentDataEntity)
            }
        }.execute().readText()
    } catch (ex: Exception) {
        null
    }

    override suspend fun putPaymentResult(
        headerAuth: String,
        singlePaymentID: String
    ) = try {
        this.defaultHttpClient.put<HttpStatement> {

            url {
                path("updatePaymentToken")
                header(key = "Authorization", value = headerAuth)
                parameter("token", singlePaymentID)
            }
        }.execute().readText().toBoolean()
    } catch (ex: Exception) {
        false
    }
}
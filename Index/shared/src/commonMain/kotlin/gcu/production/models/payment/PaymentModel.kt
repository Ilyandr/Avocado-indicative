package gcu.production.models.payment

import kotlinx.serialization.Serializable

@Serializable
data class PaymentModel(
    var payment_token: String? = null,
    var amount: Amount? = null,
    var capture: Boolean = false,
    var description: String? = null
)

package gcu.production.models.user

import kotlinx.serialization.Serializable

@Serializable
data class UserInputEntity(
    var id: String? = null,
    var name: String? = null,
    var phone: String? = null,
    var password: String? = null,
    var jobTitle: String? = null,
    var organizationId: Long? = null,
    var email: String? = null,
    var role: String? = null
)
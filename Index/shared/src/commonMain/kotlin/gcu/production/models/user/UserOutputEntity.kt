package gcu.production.models.user

import kotlinx.serialization.Serializable

@Serializable
data class UserOutputEntity(
    val name: String,
    val password: String,
    val phone: String,
    val email: String
)
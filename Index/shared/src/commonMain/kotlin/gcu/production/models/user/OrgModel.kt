package gcu.production.models.user

import kotlinx.serialization.Serializable

@Serializable
data class OrgModel(
    val id: String? = null,
    val name: String? = null,
    val creatorId: String? = null,
    val payToken: String? = null,
    val lastCheckToken: String? = null
)
package gcu.production.models.user

import saschpe.kase64.base64Encoded

data class AuthEntity(
    val login: String, val password: String
) {
    fun requireAuthData() =
        "Basic ${"$login:$password".base64Encoded}"
}
package gcu.production.index.android.domain.models.auth

import androidx.annotation.StringRes

internal sealed class RegisterUserModel {

    internal object DefaultState : RegisterUserModel()

    internal object LoadingState : RegisterUserModel()

    internal data class FaultState(
        @StringRes val messageId: Int
    ) : RegisterUserModel()

    internal data class SuccessState(
        val email: String
    ) : RegisterUserModel()
}
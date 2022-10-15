package gcu.production.index.android.domain.models.auth

import androidx.annotation.StringRes
import gcu.production.models.user.AuthEntity

internal sealed class AuthModel {

    internal object DefaultState : AuthModel()

    internal data class LoadingState(
        val data: AuthEntity
    ) : AuthModel()

    internal object SuccessState : AuthModel()

    internal data class FaultState(
        @StringRes val messageId: Int
    ) : AuthModel()
}
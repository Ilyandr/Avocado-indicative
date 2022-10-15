package gcu.production.index.android.domain.models.basic

import androidx.annotation.StringRes

internal sealed class RegisterOrgModel {

    internal object DefaultState : RegisterOrgModel()

    internal object LoadingState : RegisterOrgModel()

    internal object SuccessState : RegisterOrgModel()

    internal data class FaultState(
        @StringRes val messageId: Int
    ) : RegisterOrgModel()
}
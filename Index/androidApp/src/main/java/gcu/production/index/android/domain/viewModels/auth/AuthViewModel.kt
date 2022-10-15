package gcu.production.index.android.domain.viewModels.auth

import androidx.lifecycle.viewModelScope
import gcu.production.index.android.repository.source.architecture.viewModels.FlowableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import gcu.production.engine.EngineSDK
import gcu.production.engine.di.restAPI
import gcu.production.index.android.R
import gcu.production.index.android.domain.models.auth.AuthModel
import gcu.production.index.android.repository.features.FlowSupport.set
import gcu.production.index.android.repository.features.storage.DataPreferences
import gcu.production.index.android.repository.features.storage.DataPreferences.Companion.LOGIN_ID
import gcu.production.index.android.repository.features.storage.DataPreferences.Companion.PASSWORD_ID
import gcu.production.models.user.AuthEntity
import gcu.production.service.DataCorrectness.checkCorrectLogin
import gcu.production.service.DataCorrectness.checkCorrectPassword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class AuthViewModel @Inject constructor(
    preferences: DataPreferences
) : FlowableViewModel<AuthModel>() {

    private val mutableStateFlow =
        MutableStateFlow<AuthModel>(
            AuthModel.DefaultState
        )

    override val liveDataState =
        mutableStateFlow.asStateFlow()

    init {
        this.liveDataState.onEach { currentState ->
            when (currentState) {
                is AuthModel.LoadingState -> {

                    if (checkCorrectPassword(currentState.data.password)
                        && checkCorrectLogin(currentState.data.login)
                    )
                        successCheckLogin(
                            preferences, currentState.data
                        )
                    else
                        faultInputData()
                }

                else -> return@onEach
            }
        }.launchIn(viewModelScope)
    }

    infix fun actionCheckData(data: AuthEntity) =
        this.mutableStateFlow.set(
            AuthModel.LoadingState(
                data
            )
        )

    fun actionAlreadyExist() =
        this.mutableStateFlow.set(
            AuthModel.SuccessState
        )

    private fun successCheckLogin(
        preferences: DataPreferences, data: AuthEntity
    ) {
        viewModelScope.launch(Dispatchers.Main) {
            withContext(Dispatchers.IO) {
                EngineSDK
                    .restAPI
                    .restAuthRepository
                    .login(data)
            }.run {
                if (this != null) {

                    preferences.actionWithAuth(
                        LOGIN_ID, this.email
                    )

                    preferences.actionWithAuth(
                        PASSWORD_ID, data.password
                    )

                    mutableStateFlow.set(
                        AuthModel.SuccessState
                    )
                } else
                    faultInputData()
            }
        }
    }

    private fun faultInputData() =
        mutableStateFlow.set(
            AuthModel.FaultState(
                R.string.toastErrorCommonCheckData
            )
        )

    override fun actionReady() {
        mutableStateFlow.set(
            AuthModel.DefaultState
        )
    }
}
package gcu.production.index.android.domain.viewModels.auth

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import gcu.production.engine.EngineSDK
import gcu.production.engine.di.restAPI
import gcu.production.index.android.R
import gcu.production.index.android.domain.models.auth.RegisterUserModel
import gcu.production.index.android.repository.features.FlowSupport.set
import gcu.production.index.android.repository.source.architecture.viewModels.FlowableViewModel
import gcu.production.models.user.UserOutputEntity
import gcu.production.service.DataCorrectness.checkCorrectLogin
import gcu.production.service.DataCorrectness.checkCorrectName
import gcu.production.service.DataCorrectness.checkCorrectPassword
import gcu.production.service.DataCorrectness.checkCorrectPhone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class RegisterUserViewModel @Inject constructor() :
    FlowableViewModel<RegisterUserModel>() {

    private val mutableFlowState =
        MutableStateFlow<RegisterUserModel>(
            RegisterUserModel.DefaultState
        )

    override val liveDataState: StateFlow<RegisterUserModel> =
        mutableFlowState.asStateFlow()

    init {
        this.mutableFlowState.onEach {

        }.launchIn(viewModelScope)
    }

    override fun actionReady() {
        mutableFlowState.set(
            RegisterUserModel.DefaultState
        )
    }

    infix fun actionLoadingRegister(
        data: UserOutputEntity
    ) {
        mutableFlowState.set(
            RegisterUserModel.LoadingState
        )

        if (checkCorrectPassword(data.password)
            && checkCorrectLogin(data.email)
            && checkCorrectName(data.name)
            && checkCorrectPhone(data.phone)
        )
            viewModelScope.launch(Dispatchers.Main) {
                withContext(Dispatchers.IO) {
                    EngineSDK.restAPI.restAuthRepository.registration(data)
                }.run {

                    if (this)
                        mutableFlowState.set(
                            RegisterUserModel.SuccessState(
                                data.email
                            )
                        )
                    else
                        RegisterUserModel.FaultState(
                            R.string.toastErrorResponse
                        )
                }
            }
        else
            mutableFlowState.set(
                RegisterUserModel.FaultState(
                    R.string.toastErrorCommonCheckData
                )
            )
    }
}
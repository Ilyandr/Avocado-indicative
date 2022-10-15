package gcu.production.index.android.domain.viewModels.basic

import dagger.hilt.android.lifecycle.HiltViewModel
import gcu.production.engine.EngineSDK
import gcu.production.engine.di.restAPI
import gcu.production.index.android.R
import gcu.production.index.android.domain.models.basic.RegisterOrgModel
import gcu.production.index.android.repository.features.FlowSupport.set
import gcu.production.index.android.repository.features.storage.DataPreferences
import gcu.production.index.android.repository.source.architecture.viewModels.FlowableViewModel
import gcu.production.models.user.OrgModel
import gcu.production.service.DataCorrectness.checkCorrectOrg
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
internal class RegisterOrgViewModel @Inject constructor(
    private val preferences: DataPreferences
) : FlowableViewModel<RegisterOrgModel>() {

    private val mutableFlowState =
        MutableStateFlow<RegisterOrgModel>(
            RegisterOrgModel.DefaultState
        )

    override val liveDataState: StateFlow<RegisterOrgModel> =
        this.mutableFlowState.asStateFlow()

    infix fun launchRegisterOrg(data: OrgModel) {

        if (checkCorrectOrg(data.name))
            CoroutineScope(Dispatchers.Main).launch {

                withContext(Dispatchers.IO) {

                    preferences.getAuthModel()?.run {
                        EngineSDK.restAPI.restOrgRepository.createOrganization(
                            this, data
                        )
                    } ?: false
                }.run {
                    mutableFlowState.set(
                        if (this)
                            RegisterOrgModel.SuccessState
                        else
                            RegisterOrgModel.FaultState(
                                R.string.errorCreateOrg
                            )
                    )
                }
            }
        else
            RegisterOrgModel.FaultState(
                R.string.toastErrorCheckData
            )
    }

    override fun actionReady() {
        this.mutableFlowState.set(
            RegisterOrgModel.DefaultState
        )
    }
}
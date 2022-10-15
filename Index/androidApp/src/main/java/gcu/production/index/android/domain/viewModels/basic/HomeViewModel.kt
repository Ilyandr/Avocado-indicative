package gcu.production.index.android.domain.viewModels.basic

import gcu.production.index.android.domain.models.basic.HomeModel
import gcu.production.index.android.repository.features.FlowSupport.set
import gcu.production.index.android.repository.source.architecture.viewModels.FlowableViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class HomeViewModel : FlowableViewModel<HomeModel>() {

    private val mutableFlowState =
        MutableStateFlow<HomeModel>(
            HomeModel.DefaultState
        )

    override val liveDataState: StateFlow<HomeModel> =
        mutableFlowState.asStateFlow()

    override fun actionReady() {
        mutableFlowState.set(
            HomeModel.DefaultState
        )
    }
}
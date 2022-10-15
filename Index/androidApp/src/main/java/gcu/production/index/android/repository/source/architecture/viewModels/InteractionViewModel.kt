package gcu.production.index.android.repository.source.architecture.viewModels

import kotlinx.coroutines.flow.StateFlow

internal interface InteractionViewModel <LiveDataType>
{
    val liveDataState: StateFlow<LiveDataType>
    fun actionReady()
}
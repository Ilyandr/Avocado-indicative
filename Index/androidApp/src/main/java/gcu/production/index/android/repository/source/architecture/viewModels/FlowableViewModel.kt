package gcu.production.index.android.repository.source.architecture.viewModels

import androidx.lifecycle.ViewModel

internal abstract class FlowableViewModel <T>
    : ViewModel(), InteractionViewModel<T>

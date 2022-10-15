package gcu.production.index.android.repository.source.architecture.view

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.example.mcard.repository.source.architecture.view.StructView
import gcu.production.index.android.repository.source.architecture.viewModels.FlowableViewModel

internal abstract class FlowableFragment<T>(
    @LayoutRes layoutId: Int
) : Fragment(layoutId), StructView {

    abstract val viewModel: FlowableViewModel<T>

    abstract val viewBinding: ViewBinding
}
package gcu.production.index.android.presentation.controllers.basic

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.hoc081098.viewbindingdelegate.viewBinding
import gcu.production.index.android.R
import gcu.production.index.android.databinding.FragmentHomeBinding
import gcu.production.index.android.domain.models.basic.HomeModel
import gcu.production.index.android.domain.viewModels.basic.HomeViewModel
import gcu.production.index.android.presentation.controllers.auth.AuthFragment
import gcu.production.index.android.presentation.support.setHomeFragmentActions
import gcu.production.index.android.repository.source.architecture.view.FlowableFragment

internal class HomeFragment : FlowableFragment<HomeModel>(
    R.layout.fragment_home
) {
    override val viewModel by viewModels<HomeViewModel>()

    override val viewBinding by viewBinding<FragmentHomeBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        basicAction()
    }

    private fun initResultListener() =
        setFragmentResultListener(AuthFragment.NAVIGATE_ACTION) { _, args ->

        }

    override fun basicAction() {
        viewBinding.setHomeFragmentActions(
            requireActivity()
        )
    }
}
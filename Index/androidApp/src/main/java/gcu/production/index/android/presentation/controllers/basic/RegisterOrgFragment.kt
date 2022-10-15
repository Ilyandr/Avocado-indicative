package gcu.production.index.android.presentation.controllers.basic

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hoc081098.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import gcu.production.index.android.R
import gcu.production.index.android.databinding.IncludeOrgRegisterBinding
import gcu.production.index.android.domain.models.basic.RegisterOrgModel
import gcu.production.index.android.domain.viewModels.basic.RegisterOrgViewModel
import gcu.production.index.android.presentation.controllers.auth.AuthFragment
import gcu.production.index.android.presentation.support.setRegisterOrgFragmentActions
import gcu.production.index.android.presentation.view.custom.CustomDialogBuilder
import gcu.production.index.android.presentation.view.showMessage
import gcu.production.index.android.repository.source.architecture.view.FlowableFragment
import gcu.production.index.android.repository.source.usage.UsageDialogSource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
internal class RegisterOrgFragment : FlowableFragment<RegisterOrgModel>(
    R.layout.include_org_register
) {
    override val viewModel by viewModels<RegisterOrgViewModel>()

    override val viewBinding by viewBinding<IncludeOrgRegisterBinding>()

    private val waitingDialog: UsageDialogSource by lazy {
        CustomDialogBuilder(requireActivity())
            .setWaitingDialog()
            .build()
    }

    init {
        lifecycleScope.launchWhenStarted {
            viewModel.liveDataState.onEach { currentState ->

                when (currentState) {
                    is RegisterOrgModel.LoadingState ->
                        waitingDialog.show()

                    is RegisterOrgModel.FaultState -> {
                        waitingDialog.hide()
                        requireContext() showMessage currentState.messageId
                    }

                    is RegisterOrgModel.SuccessState -> {

                        waitingDialog.hide()
                        requireContext() showMessage R.string.createOrgSuccessMessage

                        setFragmentResult(
                            AuthFragment.NAVIGATE_ACTION,
                            bundleOf()
                        )

                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }

                    else -> return@onEach
                }

                viewModel.actionReady()
            }.collect()
        }
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        basicAction()
    }

    override fun basicAction() {

        viewBinding.setRegisterOrgFragmentActions(
            waitingDialog, viewModel::launchRegisterOrg
        )
    }
}
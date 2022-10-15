package gcu.production.index.android.presentation.controllers.auth

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hoc081098.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import gcu.production.index.android.R
import gcu.production.index.android.databinding.IncludeUserRegisterBinding
import gcu.production.index.android.domain.models.auth.RegisterUserModel
import gcu.production.index.android.domain.viewModels.auth.RegisterUserViewModel
import gcu.production.index.android.presentation.controllers.auth.AuthFragment.Companion.NAVIGATE_ACTION
import gcu.production.index.android.presentation.support.setRegisterFragmentActions
import gcu.production.index.android.presentation.view.custom.CustomDialogBuilder
import gcu.production.index.android.presentation.view.showMessage
import gcu.production.index.android.repository.features.unitAction
import gcu.production.index.android.repository.source.architecture.view.FlowableFragment
import gcu.production.index.android.repository.source.usage.UsageDialogSource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
internal class RegisterUserFragment(
    private val closeParentAction: unitAction
) : FlowableFragment<RegisterUserModel>(
    R.layout.include_user_register
) {
    override val viewModel: RegisterUserViewModel by viewModels()

    override val viewBinding by viewBinding<IncludeUserRegisterBinding>()

    private val waitingDialog: UsageDialogSource by lazy {
        CustomDialogBuilder(
            requireActivity()
        ).setWaitingDialog().build()
    }

    init {
        lifecycleScope.launchWhenStarted {
            viewModel.liveDataState.onEach {
                when (it) {
                    is RegisterUserModel.LoadingState ->
                        waitingDialog.show()

                    is RegisterUserModel.FaultState -> {
                        waitingDialog.hide()
                        requireContext() showMessage it.messageId
                    }

                    is RegisterUserModel.SuccessState -> {
                        waitingDialog.hide()

                        CustomDialogBuilder(requireActivity())
                            .setTitle(R.string.register)
                            .setMessage(
                                "${
                                    getString(
                                        R.string.messageSuccessRegister
                                    )
                                } -${
                                    it.email
                                }"
                            ).setPositiveAction {
                                setFragmentResult(
                                    NAVIGATE_ACTION,
                                    bundleOf()
                                )

                                closeParentAction.invoke()
                            }.build().show()
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
        viewBinding setRegisterFragmentActions viewModel
    }
}
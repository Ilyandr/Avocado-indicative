package gcu.production.index.android.presentation.controllers.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hoc081098.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import gcu.production.index.android.R
import gcu.production.index.android.databinding.FragmentAuthBinding
import gcu.production.index.android.domain.models.auth.AuthModel
import gcu.production.index.android.domain.viewModels.auth.AuthViewModel
import gcu.production.index.android.presentation.support.setAuthFragmentActions
import gcu.production.index.android.presentation.view.custom.CustomDialogBuilder
import gcu.production.index.android.presentation.view.showSnackbar
import gcu.production.index.android.repository.features.navigateTo
import gcu.production.index.android.repository.source.architecture.view.FlowableFragment
import gcu.production.index.android.repository.source.usage.UsageDialogSource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
internal class AuthFragment : FlowableFragment<AuthModel>(
    R.layout.fragment_auth
) {
    override val viewBinding by viewBinding<FragmentAuthBinding>()

    override val viewModel: AuthViewModel by viewModels()

    private val waitingDialog: UsageDialogSource by lazy {
        CustomDialogBuilder(
            requireActivity()
        ).setWaitingDialog().build()
    }

    init {
        lifecycleScope.launchWhenStarted {
            viewModel.liveDataState.onEach {

                when (it) {
                    is AuthModel.LoadingState ->
                        waitingDialog.show()

                    is AuthModel.FaultState -> {
                        waitingDialog.hide()
                        viewBinding.root showSnackbar it.messageId
                    }

                    is AuthModel.SuccessState -> {
                        waitingDialog.hide()

                        viewBinding.root.navigateTo(
                            R.id.action_authFragment_to_homeAdminFragment
                        )
                    }

                    else -> return@onEach
                }

                viewModel.actionReady()
            }.collect()
        }
    }

    private fun initResultListener() =
        setFragmentResultListener(NAVIGATE_ACTION) { _, _ ->

            viewModel.actionAlreadyExist()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initResultListener()
    }

    override fun onViewCreated(
        view: View, savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        basicAction()
    }

    override fun basicAction() {
        viewBinding.setAuthFragmentActions(
            viewModel, requireActivity().supportFragmentManager
        )
    }

    companion object {
        internal const val NAVIGATE_ACTION = "navigate_action"
    }
}
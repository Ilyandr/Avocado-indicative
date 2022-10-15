package gcu.production.index.android.presentation.support

import androidx.fragment.app.FragmentManager
import gcu.production.index.android.databinding.FragmentAuthBinding
import gcu.production.index.android.databinding.IncludeUserRegisterBinding
import gcu.production.index.android.domain.viewModels.auth.AuthViewModel
import gcu.production.index.android.domain.viewModels.auth.RegisterUserViewModel
import gcu.production.index.android.presentation.controllers.auth.RegisterPager
import gcu.production.models.user.AuthEntity
import gcu.production.models.user.UserOutputEntity

internal fun FragmentAuthBinding.setAuthFragmentActions(
    viewModel: AuthViewModel, fragmentManager: FragmentManager
) {

    btnNextStage.setOnClickListener {
        viewModel.actionCheckData(
            AuthEntity(
                loginView.text.toString(), passwordView.text.toString()
            )
        )
    }

    registerButton.setOnClickListener {
        RegisterPager(0).show(
            fragmentManager, null
        )
    }

    restoreButton.setOnClickListener {
        RegisterPager(1).show(
            fragmentManager, null
        )
    }
}

internal infix fun IncludeUserRegisterBinding.setRegisterFragmentActions(
    viewModel: RegisterUserViewModel
) {
    btnNextStage.setOnClickListener {
        viewModel actionLoadingRegister
                UserOutputEntity(
                    name = nameView.text.toString(),
                    password = passwordView.text.toString(),
                    phone = phoneView.text.toString(),
                    email = loginView.text.toString()
                )
    }
}
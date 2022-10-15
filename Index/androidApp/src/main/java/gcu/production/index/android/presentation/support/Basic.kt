package gcu.production.index.android.presentation.support

import androidx.fragment.app.FragmentActivity
import gcu.production.index.android.R
import gcu.production.index.android.databinding.FragmentHomeBinding
import gcu.production.index.android.databinding.IncludeOrgRegisterBinding
import gcu.production.index.android.presentation.view.initViewToolBar
import gcu.production.index.android.repository.features.changeVisibleBottomNavBar
import gcu.production.index.android.repository.features.dataOrgAction
import gcu.production.index.android.repository.features.navigateTo
import gcu.production.index.android.repository.source.usage.UsageDialogSource
import gcu.production.models.user.OrgModel

internal fun FragmentHomeBinding.setHomeFragmentActions(
    fragmentActivity: FragmentActivity
) {
    fragmentActivity changeVisibleBottomNavBar true

    content.emptyView.createOrgButton.setOnClickListener {
        root.navigateTo(
            R.id.action_homeAdminFragment_to_registerOrgFragment
        )
    }
}

internal inline fun IncludeOrgRegisterBinding.setRegisterOrgFragmentActions(
    waitingDialog: UsageDialogSource,
    crossinline registerAction: dataOrgAction
) {
    layoutTopBar.initViewToolBar()
    layoutTopBar.setTitle(R.string.createOrgFragmentTitle)

    completeButton.setOnClickListener {

        waitingDialog.show()

        registerAction.invoke(
            OrgModel(
                name = nameView.text.toString()
            )
        )
    }
}
package gcu.production.index.android.presentation.controllers.other

import android.app.Dialog
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import gcu.production.index.android.presentation.view.custom.CustomDialog
import gcu.production.index.android.repository.source.usage.DialogFragmentSource
import gcu.production.index.android.repository.source.usage.UsageDialogSource

internal class DialogFragmentSource(
    private val hostFragment: FragmentActivity,
) : DialogFragment(),
    DialogFragmentSource, UsageDialogSource {

    private lateinit var customDialog: CustomDialog

    override fun onCreateDialog(
        savedInstanceState: Bundle?,
    ): Dialog =
        if (::customDialog.isInitialized)
            this.customDialog
        else
            super.onCreateDialog(savedInstanceState)

    override fun setCustomDialog(
        dialog: CustomDialog, isCancelable: Boolean
    ): DialogFragmentSource {
        this.isCancelable = isCancelable
        this.customDialog = dialog
        return this
    }

    override fun hide() =
        try {
            this.customDialog.hide()
            this.dismiss()
        } catch (_: IllegalStateException) {
        }

    override fun show() =
        this.show(
            hostFragment.supportFragmentManager, null
        )

    override fun onConfigurationChanged(
        newConfig: Configuration,
    ) {
        super.onConfigurationChanged(newConfig)

        if (::customDialog.isInitialized)
            customDialog.updateConfigOptions()
    }
}
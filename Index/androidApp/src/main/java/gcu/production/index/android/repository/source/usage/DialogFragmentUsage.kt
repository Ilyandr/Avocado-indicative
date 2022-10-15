package gcu.production.index.android.repository.source.usage

import gcu.production.index.android.presentation.view.custom.CustomDialog

internal interface DialogFragmentSource {

    fun setCustomDialog(
        dialog: CustomDialog,
        isCancelable: Boolean
    ): DialogFragmentSource
}

internal interface UsageDialogSource {

    fun hide()

    fun show()
}
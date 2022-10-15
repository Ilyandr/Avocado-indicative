package gcu.production.index.android.presentation.view.custom

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import gcu.production.index.android.R
import gcu.production.index.android.databinding.DialogDefaultFormBinding
import gcu.production.index.android.repository.features.currentScreenPositionIsVertical
import gcu.production.index.android.repository.features.getDisplaySize
import gcu.production.index.android.repository.features.setVisible
import gcu.production.index.android.repository.features.unitAction
import gcu.production.index.android.repository.source.usage.UsageDialogSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

internal class CustomDialog(
    context: Context,
    private val cancelData: Boolean,
    private val isWaitingCall: Boolean,
    private val messageId: Int?,
    private var messageText: String?,
    private var titleId: Int,
    private var positiveAction: unitAction?,
    private var negativeAction: unitAction?,
    private var additionalAction: unitAction?,
) : Dialog(context) {

    private val dialogBinding: DialogDefaultFormBinding by lazy {
        DialogDefaultFormBinding.inflate(layoutInflater)
    }

    init {
        setContentView(dialogBinding.root)
        updateConfigOptions()
        optionalSettings()
    }

    fun updateConfigOptions() {

        setCancelable(cancelData)

        if (isWaitingCall)
            return

        getDisplaySize().run {

            (if (context.currentScreenPositionIsVertical())
                0.90
            else
                0.60)
                .run widthRatio@{

                    window?.setLayout(
                        (first * this@widthRatio).toInt(),
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
        }
    }

    private fun optionalSettings() {

        this.dialogBinding.apply {

            window?.setBackgroundDrawable(
                ColorDrawable(
                    Color.TRANSPARENT
                )
            )

            if (isWaitingCall) {

                dialogBinding.loadingBar setVisible true
                dialogBinding.panelSeparator setVisible false

                setOnCancelListener {
                    hide()
                }

                setCancelable(false)
                return@apply
            }

            titleTextView.setText(titleId)

            messageTextView.text =
                messageId?.let {
                    context.getString(it)
                } ?: messageText ?: ""

            if (negativeAction == null || positiveAction == null) {
                singleDialogControlPanel.root.visibility = View.VISIBLE

                singleDialogControlPanel.btnAdditional.setOnClickListener {
                    cancel()
                }

                return@apply
            }


            positiveAction.apply {
                monoDialogControlPanel.root.visibility = View.VISIBLE
                monoDialogControlPanel.btnPositive.setOnClickListener {
                    cancel()
                    this?.invoke()
                }
            }

            negativeAction.apply {
                monoDialogControlPanel.root.visibility = View.VISIBLE
                monoDialogControlPanel.btnNegative.setOnClickListener {
                    cancel()
                    this?.invoke()
                }
            }

            if (negativeAction != null || positiveAction != null)
                additionalAction?.apply {
                    monoDialogControlPanel.root.visibility = View.GONE
                    singleDialogControlPanel.root.visibility = View.VISIBLE

                    singleDialogControlPanel.btnAdditional.setOnClickListener {
                        cancel()
                        invoke()
                    }
                }

            if (titleId == R.string.empty)
                titleTextView.visibility =
                    View.GONE
        }
    }

    override fun show() {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                super.show()
            } catch (_: WindowManager.BadTokenException) {
            }
        }
    }

    override fun hide() {
        CoroutineScope(Dispatchers.Main).launch {
            setCancelable(true)
            cancel()
            super.hide()
        }
    }
}

internal class CustomDialogBuilder(
    private val context: Context,
) {
    private var messageId: Int = R.string.empty
    private var messageText: String = ""
    private var titleId: Int = R.string.empty
    private var isCancelable = true
    private var isWaitingCall = false

    private var positiveAction: unitAction? = null
    private var negativeAction: unitAction? = null
    private var additionalAction: unitAction? = null

    fun setTitle(titleId: Int) = apply {
        this.titleId = titleId
    }

    fun setMessage(messageId: Int) = apply {
        this.messageId = messageId
    }

    fun setMessage(messageText: String) = apply {
        this.messageText = messageText
    }

    fun setCancelable(isCancelable: Boolean) = apply {
        this.isCancelable = isCancelable
    }

    fun setPositiveAction(action: unitAction) = apply {
        this.positiveAction = action
    }

    fun setNegativeAction(action: unitAction) = apply {
        this.negativeAction = action
    }

    fun setAdditionalAction(action: unitAction) = apply {
        this.additionalAction = action
    }

    fun setWaitingDialog() = apply {
        isCancelable = false
        isWaitingCall = true
    }

    fun build() =
        (gcu.production.index.android.presentation.controllers.other.DialogFragmentSource(
            context as AppCompatActivity
        ) as gcu.production.index.android.repository.source.usage.DialogFragmentSource).setCustomDialog(
            CustomDialog(
                context = context,
                titleId = titleId,
                cancelData = isCancelable,
                isWaitingCall = isWaitingCall,
                messageId = messageId,
                messageText = messageText,
                positiveAction = positiveAction,
                negativeAction = negativeAction,
                additionalAction = additionalAction
            ), isCancelable
        ).run {
            this as UsageDialogSource
        }
}
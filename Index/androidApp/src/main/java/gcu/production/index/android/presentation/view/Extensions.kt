package gcu.production.index.android.presentation.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import gcu.production.index.android.R
import gcu.production.index.android.databinding.CustomToastLayoutBinding
import gcu.production.index.android.presentation.controllers.HostActivity
import gcu.production.index.android.repository.features.setVisible
import gcu.production.index.android.repository.features.unitAction
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch


internal infix fun View.showSnackbar(
    message: Int,
) =
    Snackbar.make(
        this, message, Snackbar.ANIMATION_MODE_SLIDE
    ).show()

@SuppressLint("InflateParams")
internal inline infix fun <reified Type, reified Host : Context> Host.showMessage(
    message: Type,
) =
    Toast(this).apply toast@{
        CustomToastLayoutBinding.bind(
            LayoutInflater.from(
                this@showMessage
            ).inflate(
                R.layout.custom_toast_layout, null
            )
        ).apply {

            when (message) {
                is Int -> this.messageView.setText(message)
                is String -> this.messageView.text = message
                else -> return@toast
            }

            @Suppress("DEPRECATION")
            view = root
            duration = Toast.LENGTH_LONG
        }
    }.show()

fun View.show() {
    setVisible(true)
}

fun View.hide() {
    setVisible(false)
}

internal fun MaterialToolbar.initViewToolBar(
    menuId: Int? = null,
    vararg buttonActions: Pair<Int, unitAction>,
) =
    MainScope().launch(Dispatchers.Main) {

        menuId?.let {
            inflateMenu(it)
        }

        setNavigationOnClickListener {
            (context as? HostActivity)?.actionBackStackCurrentFragment()
        }

        setOnMenuItemClickListener listener@{ selectedItem ->
            buttonActions.forEach { singleButtonAction ->
                if (singleButtonAction.first == selectedItem.itemId)
                    singleButtonAction.second.invoke()
            }
            return@listener true
        }
    }
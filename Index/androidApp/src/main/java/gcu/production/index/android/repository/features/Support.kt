package gcu.production.index.android.repository.features

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.FragmentActivity
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import gcu.production.index.android.R
import gcu.production.index.android.presentation.controllers.HostActivity
import gcu.production.models.user.OrgModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

internal typealias unitAction = () -> Unit

internal typealias dataNumberAction = (data: Int) -> Unit

internal typealias dataPaymentAction = (price: Double) -> Unit

internal typealias dataOrgAction = (data: OrgModel) -> Unit

internal object FlowSupport {

    infix fun <T : Any?> MutableStateFlow<T>.set(newState: T) =
        apply {
            value = newState
        }
}

internal fun View.navigateTo(
    destinationId: Int,
    args: Bundle? = null,
) = CoroutineScope(Dispatchers.Main).launch {
    Navigation
        .findNavController(this@navigateTo)
        .navigate(
            destinationId,
            args,
            navOptions = navOptions {
                anim {
                    this.enter = R.anim.nav_default_enter_anim
                    this.exit = R.anim.nav_default_exit_anim
                    this.popEnter = R.anim.nav_default_pop_enter_anim
                    this.popExit = R.anim.nav_default_pop_exit_anim
                }
            }
        )
}

internal infix fun View.setVisible(isVisible: Boolean) =
    apply {
        visibility =
            if (isVisible)
                View.VISIBLE
            else
                View.GONE
    }

internal fun getDisplaySize() =
    Resources.getSystem().displayMetrics.run {
        Pair(
            this.widthPixels, this.heightPixels
        )
    }

internal fun Context.currentScreenPositionIsVertical() =
    this.resources.configuration.orientation ==
            Configuration.ORIENTATION_PORTRAIT

internal infix fun FragmentActivity.changeVisibleBottomNavBar(isVisible: Boolean) =
    (this as HostActivity)::changeVisibleBottomNavBar.invoke(isVisible)

internal fun FragmentActivity.navigateToHome() =
    (this as HostActivity)::navigateToHome.invoke()

internal inline infix fun <reified T> Bundle?.extractParcelable(
    key: String,
): T? =
    if (Build.VERSION.SDK_INT >= 33)
        this?.getParcelable(key, T::class.java)
    else
        @Suppress("DEPRECATION")
        this?.getParcelable(key) as? T

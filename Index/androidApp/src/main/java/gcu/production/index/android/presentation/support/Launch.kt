package gcu.production.index.android.presentation.support

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.ui.setupWithNavController
import gcu.production.index.android.databinding.ActivityMainBinding

internal fun ActivityMainBinding.setHostActivityActions(
    fragment: AppCompatActivity,
    navController: NavController,
) {

    navController.apply {
        barContainer.bottomAppBar.setupWithNavController(this)

        barContainer.bottomNavigationView.background = null
        barContainer.bottomAppBar.navigationIcon = null
    }

    /*bottomAppBar.setOnItemSelectedListener {
        navController.navigate(
            when (it.itemId) {
                R.id.home ->
                    R.id.basicFragment

                R.id.createCardFragment ->
                    R.id.createCardFragment

                R.id.globalPlatformFragment ->
                    R.id.globalPlatformFragment
                else ->
                    return@setOnItemSelectedListener false
            }
        )

        return@setOnItemSelectedListener true
    }*/

    /*content.bottomAppBar.setOnItemReselectedListener reselectedListener@{
        navController.navigate(
            when (it.itemId) {
                R.id.createCardFragment ->
                    if (navController.currentDestination?.id != R.id.manuallyCardAdd)
                        R.id.manuallyCardAdd
                    else
                        return@reselectedListener
                else ->
                    return@reselectedListener
            }
        )
    }*/
}

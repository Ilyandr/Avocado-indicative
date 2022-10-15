package gcu.production.index.android.presentation.controllers

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.mcard.repository.source.architecture.view.StructView
import gcu.production.index.android.R
import com.hoc081098.viewbindingdelegate.*
import dagger.hilt.android.AndroidEntryPoint
import gcu.production.index.android.databinding.ActivityMainBinding
import gcu.production.index.android.presentation.support.setHostActivityActions
import gcu.production.index.android.presentation.view.hide
import gcu.production.index.android.presentation.view.show
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
internal class HostActivity : AppCompatActivity(
    R.layout.activity_main
), StructView {

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(
            R.id.fragmentContainerView
        ) as NavHostFragment).navController
    }

    private val viewBinding by viewBinding<ActivityMainBinding>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(viewBinding.root)
        basicAction()
    }

    internal fun changeVisibleBottomNavBar(isVisible: Boolean) {
        if (isVisible) {
            viewBinding.barContainer.bottomAppBar.show()
            viewBinding.barContainer.addButton.show()
        }
        else {
            viewBinding.barContainer.addButton.hide()
            viewBinding.barContainer.bottomAppBar.hide()
        }
    }

    internal fun navigateToHome() {
        viewBinding.barContainer.bottomNavigationView.selectedItemId =
            R.id.homeFragment
    }

    override fun basicAction() {
        changeVisibleBottomNavBar(false)

        viewBinding.setHostActivityActions(
            this, navController
        )
    }

    internal fun actionBackStackCurrentFragment() =
        lifecycleScope.launch(Dispatchers.Main) {
            navController.popBackStack()
        }
}

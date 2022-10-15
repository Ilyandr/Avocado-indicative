package gcu.production.index.android.presentation.adapters

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import gcu.production.index.android.presentation.controllers.auth.RegisterUserFragment
import gcu.production.index.android.presentation.controllers.auth.RestoreFragment
import gcu.production.index.android.repository.features.unitAction

internal class ViewPagerAdapter(
    manager: FragmentManager,
    lifecycle: Lifecycle,
    currentPage: Int,
    closeParentAction: unitAction
) : FragmentStateAdapter(manager, lifecycle) {

    private val data =
        listOf(
            RegisterUserFragment(closeParentAction),
            RestoreFragment()
        ).run {
            if (currentPage == 1)
                reversed()
            else
                this
        }

    override fun getItemCount() =
        this.data.size

    override fun createFragment(
        position: Int
    ) = data[position]
}
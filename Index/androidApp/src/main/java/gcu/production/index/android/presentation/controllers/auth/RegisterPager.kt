package gcu.production.index.android.presentation.controllers.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.example.mcard.repository.source.architecture.view.StructView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.tabs.TabLayoutMediator
import gcu.production.index.android.R
import gcu.production.index.android.databinding.FragmentRegisterBinding
import gcu.production.index.android.presentation.adapters.ViewPagerAdapter


internal class RegisterPager(
    private val currentPage: Int
) : BottomSheetDialogFragment(), StructView {

    private lateinit var viewBinding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        drawableAction()
        basicAction()
        return viewBinding.root
    }

    override fun basicAction() {
        viewBinding =
            FragmentRegisterBinding.inflate(layoutInflater)

        viewBinding.viewPager.adapter =
            ViewPagerAdapter(
                requireActivity().supportFragmentManager,
                lifecycle,
                currentPage,
                this::dismiss
            )

        resources.getStringArray(
            R.array.tabLayoutRegisterData
        ).run {

            (if (currentPage == 1)
                reversedArray()
            else
                this).run {
                TabLayoutMediator(
                    viewBinding.tabLayout, viewBinding.viewPager
                ) { tab, position ->
                    tab.text = this[position]
                }.attach()
            }

        }

        viewBinding.barLayout.setNavigationOnClickListener {
            this.dismiss()
        }
    }

    override fun getTheme() =
        R.style.CustomBottomSheetDialogTheme

    override fun drawableAction() {

        dialog?.setOnShowListener { dialog ->
            (dialog as BottomSheetDialog).findViewById<FrameLayout>(
                com.google.android.material.R.id.design_bottom_sheet
            )?.let {
                BottomSheetBehavior.from(it).run {
                    state = BottomSheetBehavior.STATE_EXPANDED
                    skipCollapsed = true
                }
            }
        }
    }
}
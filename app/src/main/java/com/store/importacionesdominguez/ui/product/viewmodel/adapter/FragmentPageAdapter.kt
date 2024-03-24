package com.store.importacionesdominguez.ui.product.viewmodel.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.lifecycle.Lifecycle
import com.store.importacionesdominguez.ui.product.view.ProductDescriptionFragment
import com.store.importacionesdominguez.ui.product.view.ProductSpecificationsFragment

class FragmentPageAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> ProductDescriptionFragment()
            1 -> ProductSpecificationsFragment()
            else -> throw IllegalArgumentException("Tab no encontrada")
        }
    }
}
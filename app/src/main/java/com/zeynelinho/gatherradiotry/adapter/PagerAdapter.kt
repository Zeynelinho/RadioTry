package com.zeynelinho.gatherradiotry.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.zeynelinho.gatherradiotry.view.FavoriesFragment
import com.zeynelinho.gatherradiotry.view.MainFragment


class PagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position){
            0 -> MainFragment()
            1 -> FavoriesFragment()
            else -> MainFragment()
        }
    }
}
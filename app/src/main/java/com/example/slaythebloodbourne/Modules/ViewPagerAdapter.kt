package com.example.slaythebloodbourne.Modules

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

class ViewPagerAdapter(fm:FragmentManager):FragmentStatePagerAdapter(fm) {

    private val fragmentList = arrayListOf<Fragment>()

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    fun addItem(item: Fragment){
        fragmentList.add(item)
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    fun removeItem(int: Int){
        fragmentList.removeAt(int)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}
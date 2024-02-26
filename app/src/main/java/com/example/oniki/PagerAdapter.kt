package com.example.oniki

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class PagerAdapter(fragmentActivity: EventsFragment) :FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount()  = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {NewEventsFragment()}
            1 -> {ApplicationsFragment()}
            2 -> {AttendEventsFragment()}
            else -> { throw Resources.NotFoundException("Position Not Found")}
        }

    }
}
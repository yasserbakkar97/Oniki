package com.example.oniki

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_events.*


class EventsFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tabLayout = view.findViewById(R.id.tabLayoutEvents)
        viewPager2 = view.findViewById(R.id.viewPagerEvents)
        viewPager2.adapter = PagerAdapter(this)
        viewPager2.isSaveEnabled = false
        TabLayoutMediator(tabLayout,viewPager2){ tab,index ->
            tab.text = when(index){
                0 -> {"New Events"}
                1 -> {"Applications"}
                2 -> {"Attend Events"}
                else -> {throw Resources.NotFoundException("Position Not Found")}
            }
        }.attach()
    }


}
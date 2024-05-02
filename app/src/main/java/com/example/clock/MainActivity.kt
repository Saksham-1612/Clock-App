package com.example.clock

import AlarmFragment
import TimerFragment
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //setting up status bar of same color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = getColor(R.color.primary)
        }

        val viewPager: ViewPager2 = findViewById(R.id.viewPager)
        val fragments =
            listOf(TimeFragment(), AlarmFragment(), StopwatchFragment(), TimerFragment())
        val adapter = ViewPagerAdapter(fragments, supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.setIcon(R.drawable.time_icon)
                    tab.text="Time"
                }

                1 -> {
                    tab.setIcon(R.drawable.alarm_icon)
                    tab.text="Alarm"
                }

                2 -> {
                    tab.setIcon(R.drawable.sand_clock_time_svgrepo_com)
                    tab.text="Stopwatch"
                }

                3 -> {
                    tab.setIcon(R.drawable.timer_icon)
                    tab.text="Timer"
                }

                else -> tab.text = "Tab ${position + 1}"
            }
        }.attach()
    }
}
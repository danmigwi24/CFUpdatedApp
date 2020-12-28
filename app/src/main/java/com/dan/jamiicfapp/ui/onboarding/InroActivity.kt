package com.dan.jamiicfapp.ui.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.dan.jamiicfapp.R
import com.dan.jamiicfapp.data.Introdata
import com.dan.jamiicfapp.ui.SplashActivity
import com.dan.jamiicfapp.ui.WelcomeActivity
import com.dan.jamiicfapp.ui.onboarding.adapter.IntroViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_intro.*

class InroActivity : AppCompatActivity() {
    private lateinit var introdata: ArrayList<Introdata>
    private lateinit var introViewPagerAdapter: IntroViewPagerAdapter
    private var position = 0
    private lateinit var animatBtn: Animation
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        setContentView(R.layout.activity_intro)


        animatBtn = AnimationUtils.loadAnimation(applicationContext, R.anim.botton_animation)
        sharedPreferences =
            applicationContext.getSharedPreferences("firstTime", Context.MODE_PRIVATE)

        if (proceedToMainActivity()) {
            startActivity(Intent(this, SplashActivity::class.java))
            finish()
        }

        introdata = ArrayList()
        introdata.add(
            Introdata(
                "Jamii CrowdFunding Application",
                "To make a donation, through mobile money.",
                R.drawable.disability
            )
        )
        introdata.add(
            Introdata(
                "Jamii CrowdFunding Application",
               "To upload information concerning a person with disability",
                R.drawable.disability
            )
        )
        introdata.add(
            Introdata(
                "Jamii CrowdFunding Application",
                " To allow the community member to create accounts",
                R.drawable.disability
            )
        )
        introdata.add(
            Introdata(
                "Jamii CrowdFunding Application",
               "\uF075Provide module where community member can give their feedback",
                R.drawable.disability
            )
        )

        introViewPagerAdapter = IntroViewPagerAdapter(this, introdata)
        screen_viewpager.adapter = introViewPagerAdapter
        tabLayoutIndicator.setupWithViewPager(screen_viewpager)

        btn_next.setOnClickListener {
            position = screen_viewpager.currentItem
            if (position < introdata.size) {
                position++
                screen_viewpager.currentItem = position
            }
            if (position == introdata.size - 1) {
                loadLastScreen()
            }
        }
        tabLayoutIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {}
            override fun onTabUnselected(tab: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab!!.position == introdata.size - 1) {
                    loadLastScreen()
                }
            }

        })

        btn_getStarted.setOnClickListener {
            startActivity(Intent(this, SplashActivity::class.java))
            saveFirstTimeToPref()
            finish()
        }
    }

    private fun proceedToMainActivity(): Boolean {
        val isOnboardingActivityOpened = sharedPreferences.getBoolean("firstTime", false)
        return isOnboardingActivityOpened
    }

    private fun saveFirstTimeToPref() {
        val editor = sharedPreferences.edit()
        editor.putBoolean("firstTime", true)
        editor.apply()
        editor.commit()
    }

    private fun loadLastScreen() {
        btn_next.visibility = View.INVISIBLE
        btn_getStarted.visibility = View.VISIBLE
        tabLayoutIndicator.visibility = View.INVISIBLE

        btn_getStarted.animation = animatBtn
    }
}
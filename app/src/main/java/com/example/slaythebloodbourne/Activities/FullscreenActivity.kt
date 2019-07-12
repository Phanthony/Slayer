package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.slaythebloodbourne.R
import androidx.viewpager.widget.ViewPager
import com.example.slaythebloodbourne.Modules.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {

    lateinit var viewPager: ViewPager
    lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hiding UI elements
        setContentView(R.layout.activity_fullscreen)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        supportActionBar?.hide()

        viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager = fragmentContainer
        val startMenu = MainMenuFragment()
        viewPagerAdapter.addItem(startMenu)
        viewPager.adapter = viewPagerAdapter
    }

    fun changeFragment(position: Int,smooth: Boolean = true){
        viewPager.setCurrentItem(position, smooth)
    }

    fun addFragment(fragment: Fragment){
        viewPagerAdapter.addItem(fragment)
        viewPagerAdapter.notifyDataSetChanged()
    }

    fun removeFragment(position: Int){
        viewPagerAdapter.removeItem(position)
        viewPagerAdapter.notifyDataSetChanged()
    }
}

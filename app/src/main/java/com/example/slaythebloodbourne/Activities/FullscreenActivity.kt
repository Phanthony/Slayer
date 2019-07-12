package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.slaythebloodbourne.R
import androidx.viewpager.widget.ViewPager
import com.example.slaythebloodbourne.Modules.ViewPagerAdapter
import kotlinx.android.synthetic.main.activity_fullscreen.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : FragmentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Hiding UI elements
        setContentView(R.layout.activity_fullscreen)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        //supportActionBar?.hide()


        if (savedInstanceState != null) {
            return
        }

        val startMenu = MainMenuFragment()

        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer, startMenu).commit()


    }

    fun replaceCurrentFragmentNoSave(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun replaceCurrentFragmentSave(fragment: Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment,"PATHWAY")
            .addToBackStack("PATHWAY")
            .commit()
    }

    fun goBackOneFragment(){
        supportFragmentManager.popBackStackImmediate()
    }

    fun createNewGame(){
        val startMenu = MainMenuFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,startMenu)
            .commit()
    }
}

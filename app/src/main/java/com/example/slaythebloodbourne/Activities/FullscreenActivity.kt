package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.Entities.Items.Cards.Card_Bite
import com.example.slaythebloodbourne.Entities.Items.Cards.Card_Block
import com.example.slaythebloodbourne.Entities.Items.Cards.Card_Strike
import com.example.slaythebloodbourne.Entities.Enemies.Bosses.Boss_Dragon
import com.example.slaythebloodbourne.Entities.Enemies.Enemy
import com.example.slaythebloodbourne.Entities.Enemies.Enemy_Zombie
import com.example.slaythebloodbourne.R

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

    fun replaceCurrentFragmentSave(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment, "PATHWAY")
            .addToBackStack("PATHWAY")
            .commit()
    }

    fun createNewGame() {
        val startMenu = MainMenuFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, startMenu)
            .commit()
    }

    fun backToPathway() {
        val pathway = supportFragmentManager.findFragmentByTag("PATHWAY")!!
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, pathway)
            .commit()
    }

    fun randomCard(player : Character): Card? {
        return when ((1..3).random()) {
            1 -> when ((1..5).random()) {
                in (1..2) -> {
                    Card_Strike(player)
                }
                in (3..4) -> {
                    Card_Block(player)
                }
                else -> Card_Bite(player)
            }
            else -> null
        }
    }

    fun randomGold(floor: Int): Int {
        return when (floor) {
            in (1..10) -> {
                (8..18).random()
            }
            in (11..20) -> {
                (17..26).random()
            }
            in (21..40) -> {
                (30..46).random()
            }
            in (41..60) -> {
                (43..64).random()
            }
            in (61..80) -> {
                (62..80).random()
            }
            else -> {
                (72..94).random()
            }
        }
    }

    fun randomEnemy(floor: Int) : Enemy{
        return Enemy_Zombie(floor)
    }

    fun randomBoss(floor: Int) : Enemy{
        return Boss_Dragon(floor)
    }
}

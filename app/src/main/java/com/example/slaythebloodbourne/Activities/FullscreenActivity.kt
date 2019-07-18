package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.WindowManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Bosses.Boss_Dragon
import com.example.slaythebloodbourne.Entities.Enemies.Enemy
import com.example.slaythebloodbourne.Entities.Enemies.Enemy_Zombie
import com.example.slaythebloodbourne.Entities.Enemies.Move
import com.example.slaythebloodbourne.Entities.Items.Cards.*
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
            .setCustomAnimations(R.animator.fragment_slide_in_left,R.animator.fragment_slide_out_left)
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }

    fun replaceCurrentFragmentSave(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out)
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
            .setCustomAnimations(R.animator.fragment_slide_in_right,R.animator.fragment_slide_out_right)
            .replace(R.id.fragmentContainer, pathway)
            .commit()
    }

    fun randomCard(player: Character): Card? {
        return when ((1..3).random()) {
            1 -> when ((1..8).random()) {
                in (1..2) -> {
                    Card_Strike(player)
                }
                in (3..4) -> {
                    Card_Block(player)
                }
                5 -> Card_Bite(player)
                6 -> Card_AttackBreak(player)
                7 -> Card_ShieldBreak(player)
                8 -> Card_Bash(player)
                else -> {
                    null
                }
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

    fun randomEnemy(floor: Int): Enemy {
        return Enemy_Zombie(floor)
    }

    fun randomBoss(floor: Int): Enemy {
        return Boss_Dragon(floor)
    }

    fun randomShrine(): Move {
        return when ((1..3).random()) {
            //Negative Effect
            1 -> {
                when((1..5).random()) {
                    in (1..2) -> { Move(-1,0) }
                    in (3..4) -> { Move(0,-1) }
                    else -> { Move(-1,-1) }
                }
            }
            //Positive effect
            else -> {
                when ((1..5).random()) {
                    in (1..2) -> { Move(1,0) }
                    in (3..4) -> { Move(0,1) }
                    else -> { Move(1,1) }
                }
            }
        }
    }
}

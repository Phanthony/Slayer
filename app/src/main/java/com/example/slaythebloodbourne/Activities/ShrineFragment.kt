package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Move
import com.example.slaythebloodbourne.R
import kotlinx.coroutines.*

class ShrineFragment(val player: Character, val effect: Move) : Fragment() {

    lateinit var mainLayout: FrameLayout
    lateinit var flashLayout: LinearLayout
    lateinit var prayingText: TextView
    lateinit var leaveShrineButton: Button
    lateinit var shrineImageLayout: LinearLayout
    lateinit var shrineNewLayout: View
    lateinit var shrineTextLayout: LinearLayout
    lateinit var shrineRewardLayout: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.shrine_fragment_layout, container, false)

        val inflaterNewLayout = LayoutInflater.from(context)

        flashLayout = view.findViewById(R.id.shrine_flash_layout)
        prayingText = view.findViewById(R.id.shrine_praying_text)
        shrineImageLayout = view.findViewById(R.id.shrine_image_layout)
        mainLayout = view.findViewById(R.id.shrine_main_layout)

        shrineNewLayout = inflaterNewLayout.inflate(R.layout.shrine_reward_layout,mainLayout,false)
        shrineRewardLayout = shrineNewLayout.findViewById(R.id.shrine_reward_layout)
        shrineTextLayout = shrineNewLayout.findViewById(R.id.shrine_text_layout)
        leaveShrineButton = shrineNewLayout.findViewById(R.id.shrine_leave_button)

        leaveShrineButton.setOnClickListener {
            player.playerBonusAttack += effect.attack
            player.playerBonusBlock += effect.block
            val main = (activity as FullscreenActivity)
            main.backToPathway()
        }

        setRewards()
        startAnimations()

        return view
    }

    fun startAnimations() {
        val inflater = LayoutInflater.from(context)
        val transitionLayout = inflater.inflate(R.layout.white_flash_transition_layout,flashLayout,false)
        val animationFlashOut = AnimationUtils.loadAnimation(this.context, R.anim.flash_out).apply {
            setAnimationListener(
                object : AnimationListener{
                    override fun onAnimationRepeat(animation: Animation?) {
                        return
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        flashLayout.removeAllViews()
                    }

                    override fun onAnimationStart(animation: Animation?) {
                        shrineImageLayout.removeAllViews()
                        mainLayout.addView(shrineNewLayout)
                    }

                }
            )
        }
        val animationFlashIn = AnimationUtils.loadAnimation(this.context, R.anim.flash_in).apply {
            setAnimationListener(
                object : AnimationListener{
                    override fun onAnimationRepeat(animation: Animation?) {
                        return
                    }

                    override fun onAnimationEnd(animation: Animation?) {

                        transitionLayout.startAnimation(animationFlashOut)
                    }

                    override fun onAnimationStart(animation: Animation?) {
                        return
                    }

                }
            )
        }


        CoroutineScope(Dispatchers.Main).launch {
            for (i in (1..2)) {
                prayingText.text = "praying to the gods."
                withContext(Dispatchers.IO) {
                    delay(800)
                }
                prayingText.text = "praying to the gods.."
                withContext(Dispatchers.IO) {
                    delay(800)
                }
                prayingText.text = "praying to the gods..."
                withContext(Dispatchers.IO) {
                    delay(800)
                }
            }
            flashLayout.addView(transitionLayout)
            transitionLayout.startAnimation(animationFlashIn)
        }
    }

    fun setRewards(){
        val inflater = LayoutInflater.from(context)
        if(effect.attack != 0){
            val attackLayout = inflater.inflate(R.layout.reward_attack_layout,shrineRewardLayout)
            attackLayout.findViewById<TextView>(R.id.reward_attack_text_view).text = "${effect.attack}"
        }
        if(effect.block != 0){
            val attackLayout = inflater.inflate(R.layout.reward_defense_layout,shrineRewardLayout)
            attackLayout.findViewById<TextView>(R.id.reward_defense_text_view).text = "${effect.block}"
        }
    }
}
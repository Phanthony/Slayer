package com.example.slaythebloodbourne.Activities

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.*
import android.widget.Button
import android.widget.FrameLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.R

class VictoryFragment(private val card: Card?, private val gold:Int, private val player: Character, private val chest: Boolean = false):Fragment() {

    var rewardCard: Button? = null
    var energyCost: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        if (card!= null){

            energyCost = TextView(this.context)
            energyCost!!.text = "${card.energyCost}"
            energyCost!!.setTextColor(Color.parseColor("#0f54fa"))
            energyCost!!.textSize = 30.0F
            energyCost!!.elevation = 10.0F
            energyCost!!.setPadding(25,25,0,0)
            energyCost!!.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT)


            rewardCard = Button(this.context)
            rewardCard!!.text = "${card.name}\n\n${card.description}"
            rewardCard!!.textSize = 25.0F
            rewardCard!!.background = ContextCompat.getDrawable(this.context!!,R.drawable.button_border)
            rewardCard!!.setTextColor(Color.parseColor("#FFFFFF"))
            rewardCard!!.setOnClickListener {
                player.playerDeck.add(card)
                player.playerDeck.shuffle()
                rewardCard!!.visibility = View.INVISIBLE
                energyCost!!.text = ""
            }
            rewardCard!!.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)


        }

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.victory_fragment_layout,container,false)

        if (rewardCard != null){
            view.findViewById<FrameLayout>(R.id.rewardCardLayout).apply {
                addView(energyCost)
                addView(rewardCard)
            }
        }

        view.findViewById<TextView>(R.id.goldGainedText).text = "$gold"
        view.findViewById<Button>(R.id.finishButton).setOnClickListener{
            player.currentGold += gold
            val main = (activity as FullscreenActivity)
            main.backToPathway()
        }

        if(chest){
            view.findViewById<TextView>(R.id.victoryText).text = ""
        }

        return view
    }
}
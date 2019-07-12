package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.R

class PathwayFragment : Fragment() {

    private lateinit var pathwayList: ArrayList<AppCompatImageButton>
    private lateinit var currentFloorTextView: TextView

    private var levelCount = 1
    private val player = Character()

    private fun startGame() {
        player.addCardsToDeck()
        setRooms()
    }

    private fun setRooms() {
        for (i in 0..2) {
            val currentRoom = pathwayList[i]
            when ((0..150).random()) {
                in (0..14) -> {
                    currentRoom.setImageResource(R.drawable.treasure_sign)
                    setButtonListener(0, currentRoom)
                }
                in (15..30) -> {
                    currentRoom.setImageResource(R.drawable.shop_sign)
                    setButtonListener(0, currentRoom)
                }
                in (31..101) -> {
                    currentRoom.setImageResource(R.drawable.enemy_sign)
                    setButtonListener(0, currentRoom)
                }
                in (102..130) -> {
                    currentRoom.setImageResource(android.R.color.darker_gray)
                    setButtonListener(0, currentRoom)
                }
            }
        }
    }


    private fun setButtonListener(code: Int, button: AppCompatImageButton) {
        //enemy -- 0
        //store -- 1
        //chest -- 2
        //boss -- 3
        //shrine -- 4
        when (code) {
            1 -> { }
            2 -> { }
            else -> {
                button.setOnClickListener {

                    val newBattle = BattleFragment(player)
                    val mainActivity = activity as FullscreenActivity
                    mainActivity.addFragment(newBattle)
                    mainActivity.changeFragment(2)

                    setRooms()
                    levelCount++
                    currentFloorTextView.text = "Floor $levelCount"
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.pathway_fragment_layout, container, false)

        val buttonPath1 = view.findViewById<AppCompatImageButton>(R.id.buttonPathOne)
        val buttonPath2 = view.findViewById<AppCompatImageButton>(R.id.buttonPathTwo)
        val buttonPath3 = view.findViewById<AppCompatImageButton>(R.id.buttonPathThree)
        currentFloorTextView = view.findViewById(R.id.textFloorCount)
        pathwayList = arrayListOf(buttonPath1, buttonPath2, buttonPath3)

        startGame()

        return view
    }
}
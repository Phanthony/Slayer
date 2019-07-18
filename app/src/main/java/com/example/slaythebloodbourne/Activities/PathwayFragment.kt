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
    private val player = Character().apply {
        addCardsToDeck()
        currentGold = 9999
        playerBonusAttack = 10
    }

    private fun setRooms() {
        if (levelCount % 10 != 0) {
            for (i in 0..2) {
                val currentRoom = pathwayList[i]
                when ((0..150).random()) {
                    in (0..14) -> {
                        currentRoom.setImageResource(R.drawable.treasure_sign)
                        setButtonListener(2, currentRoom)
                    }
                    in (15..30) -> {
                        currentRoom.setImageResource(R.drawable.shop_sign)
                        setButtonListener(1, currentRoom)
                    }
                    in (31..101) -> {
                        currentRoom.setImageResource(R.drawable.enemy_sign)
                        setButtonListener(0, currentRoom)
                    }
                    in (102..130) -> {
                        currentRoom.setImageResource(R.drawable.button_border)
                        setButtonListener(5, currentRoom)
                    }
                    in (131..150) -> {
                        currentRoom.setImageResource(R.drawable.shrine_icon)
                        setButtonListener(4, currentRoom)
                    }
                }
            }
        } else {
            for (i in 0..2) {
                val currentRoom = pathwayList[i]
                currentRoom.setImageResource(R.drawable.boss_sign)
                setButtonListener(3, currentRoom)
            }
        }
    }


    private fun setButtonListener(code: Int, button: AppCompatImageButton) {
        val main = activity as FullscreenActivity
        //enemy -- 0
        //store -- 1
        //chest -- 2
        //boss -- 3
        //shrine -- 4
        //empty -- else
        when (code) {
            0 -> {
                button.setOnClickListener {
                    val newBattle = BattleFragment(player, levelCount, main.randomEnemy(levelCount))
                    levelCount++
                    main.replaceCurrentFragmentNoSave(newBattle)
                }
            }
            1 -> {
                button.setOnClickListener {
                    val store = ShopFragment(player)
                    levelCount++
                    main.replaceCurrentFragmentNoSave(store)
                }

            }
            2 -> {
                button.setOnClickListener {
                    val rewardCard = main.randomCard(player)
                    val rewardGold = main.randomGold(levelCount)
                    val chestReward = VictoryFragment(rewardCard, rewardGold, player, true)
                    levelCount++
                    main.replaceCurrentFragmentNoSave(chestReward)
                }
            }
            3 -> {
                button.setOnClickListener {
                    val newBattle = BattleFragment(player, levelCount, main.randomBoss(levelCount))
                    levelCount++
                    main.replaceCurrentFragmentNoSave(newBattle)
                }
            }
            4 -> {
                button.setOnClickListener {
                    val shrineReward = main.randomShrine()
                    val shrine = ShrineFragment(player, shrineReward)
                    levelCount++
                    main.replaceCurrentFragmentNoSave(shrine)
                }
            }
            5 -> {
                button.setOnClickListener {
                    levelCount++
                    currentFloorTextView.text = "Floor $levelCount"
                    setRooms()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.pathway_fragment_layout, container, false)

        val buttonPath1 = view.findViewById<AppCompatImageButton>(R.id.buttonPathOne)
        val buttonPath2 = view.findViewById<AppCompatImageButton>(R.id.buttonPathTwo)
        val buttonPath3 = view.findViewById<AppCompatImageButton>(R.id.buttonPathThree)
        view.findViewById<TextView>(R.id.goldText).text = "${player.currentGold}"
        currentFloorTextView = view.findViewById(R.id.textFloorCount)
        pathwayList = arrayListOf(buttonPath1, buttonPath2, buttonPath3)
        currentFloorTextView.text = "Floor $levelCount"

        setRooms()

        return view
    }
}
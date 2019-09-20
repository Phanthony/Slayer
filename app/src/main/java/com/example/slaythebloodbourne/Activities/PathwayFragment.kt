package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.Entities.Items.Cards.Card_Block
import com.example.slaythebloodbourne.Entities.Items.Cards.Card_RaiseAttack
import com.example.slaythebloodbourne.Entities.Items.Cards.Card_Strike
import com.example.slaythebloodbourne.Modules.RecyclerViewDiscardDeckAdapter
import com.example.slaythebloodbourne.R

class PathwayFragment(roomSelection: ArrayList<Int>? = null,
                      character: Character? = null, level: Int? = null): Fragment() {

    private lateinit var pathwayList: ArrayList<AppCompatImageButton>
    private lateinit var currentFloorTextView: TextView

    private var levelCount = when(level){
        null -> 1
        else -> level
    }
    private val player = when(character){
        null -> {
            val char = Character()
            addCardsToDeck(char)
            char
        }
        else -> {
            character
        }
    }

    private fun addCardsToDeck(player: Character){
        for(i in 1..5){
            val baseStrike = Card_Strike(player)
            val baseBlock = Card_RaiseAttack(player)
            player.playerDeck.add(baseBlock)
            player.playerDeck.add(baseStrike)
        }
        player.playerDeck.shuffle()
    }

    private var rooms = when(roomSelection){
        null -> selectRooms()
        else -> roomSelection
    }

    private fun selectRooms(): ArrayList<Int> {
        val roomSelections = arrayListOf<Int>()
        if (levelCount % 10 != 0) {
            for (i in 0..2) {
                when ((0..150).random()) {
                    in (0..14) -> {
                        roomSelections.add(2)
                    }
                    in (15..30) -> {
                        roomSelections.add(1)
                    }
                    in (31..101) -> {
                        roomSelections.add(0)
                    }
                    in (102..130) -> {
                        roomSelections.add(5)
                    }
                    in (131..150) -> {
                        roomSelections.add(4)
                    }
                }
            }
        } else {
            for (i in 0..2) {
                roomSelections.add(3)
            }
        }
        return roomSelections
    }

    private fun setButtonListener(roomSelection : ArrayList<Int>) {
        val main = activity as FullscreenActivity
        //enemy -- 0
        //store -- 1
        //chest -- 2
        //boss -- 3
        //shrine -- 4
        //empty -- 5
        for(i in (0 until roomSelection.size)) {
            val currentRoom = pathwayList[i]
            when (roomSelection[i]) {
                0 -> {
                    currentRoom.setImageResource(R.drawable.enemy_sign)
                    currentRoom.setOnClickListener {
                        val enemy = main.randomEnemy(levelCount)
                        val newBattle = BattleFragment(player, levelCount, enemy)
                        levelCount++
                        rooms = selectRooms()
                        main.updateEnemyTable(enemy)
                        main.updateDatabase(rooms,player,levelCount,0)
                        main.replaceCurrentFragmentNoSave(newBattle)
                    }
                }
                1 -> {
                    currentRoom.setImageResource(R.drawable.shop_sign)
                    currentRoom.setOnClickListener {
                        val store = ShopFragment(player)
                        levelCount++
                        rooms = selectRooms()
                        main.updateDatabase(rooms,player,levelCount,1)
                        main.replaceCurrentFragmentNoSave(store)
                    }

                }
                2 -> {
                    currentRoom.setImageResource(R.drawable.treasure_sign)
                    currentRoom.setOnClickListener {
                        val rewardCard = main.randomCard(player)
                        val rewardGold = main.randomGold(levelCount)
                        val chestReward = VictoryFragment(rewardCard, rewardGold, player, true)
                        levelCount++
                        rooms = selectRooms()
                        main.updateChestTable(rewardGold,rewardCard)
                        main.updateDatabase(rooms,player,levelCount,2)
                        main.replaceCurrentFragmentNoSave(chestReward)
                    }
                }
                3 -> {
                    currentRoom.setImageResource(R.drawable.boss_sign)
                    currentRoom.setOnClickListener {
                        val boss = main.randomBoss(levelCount)
                        val newBattle = BattleFragment(player, levelCount, boss)
                        levelCount++
                        rooms = selectRooms()
                        main.updateEnemyTable(boss)
                        main.updateDatabase(rooms,player,levelCount,0)
                        main.replaceCurrentFragmentNoSave(newBattle)
                    }
                }
                4 -> {
                    currentRoom.setImageResource(R.drawable.shrine_icon)
                    currentRoom.setOnClickListener {
                        val shrineReward = main.randomShrine()
                        val shrine = ShrineFragment(player, shrineReward)
                        levelCount++
                        rooms = selectRooms()
                        main.updateShrineTable(shrineReward)
                        main.updateDatabase(rooms,player,levelCount,3)
                        main.replaceCurrentFragmentNoSave(shrine)
                    }
                }
                5 -> {
                    currentRoom.setImageResource(R.drawable.button_border)
                    currentRoom.setOnClickListener {
                        levelCount++
                        currentFloorTextView.text = "Floor $levelCount"
                        rooms = selectRooms()
                        setButtonListener(rooms)
                        main.updateDatabase(rooms,player,levelCount,5)
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val main = activity as FullscreenActivity

        main.updateDatabase(rooms,player,levelCount,5)

        val view = inflater.inflate(R.layout.pathway_fragment_layout, container, false)

        val buttonPath1 = view.findViewById<AppCompatImageButton>(R.id.buttonPathOne)
        val buttonPath2 = view.findViewById<AppCompatImageButton>(R.id.buttonPathTwo)
        val buttonPath3 = view.findViewById<AppCompatImageButton>(R.id.buttonPathThree)
        view.findViewById<TextView>(R.id.goldText).text = "${player.currentGold}"
        currentFloorTextView = view.findViewById(R.id.textFloorCount)
        pathwayList = arrayListOf(buttonPath1, buttonPath2, buttonPath3)
        currentFloorTextView.text = "Floor $levelCount"

        val adapter = RecyclerViewDiscardDeckAdapter(arrayListOf())
        val discardDeckCards = view.findViewById<RecyclerView>(R.id.discard_deck_recycler_view)
        discardDeckCards.apply {
            layoutManager = GridLayoutManager(this.context,3)
            this.adapter = adapter
        }

        val cardView = view.findViewById<LinearLayout>(R.id.discard_deck_card_view)

        val discardButton = view.findViewById<Button>(R.id.pathwayDiscardButton)
        discardButton.setOnClickListener {
            val cards = player.playerDiscard
            cards.sortBy {
                it.name
            }
            adapter.addCards(cards)
            cardView.visibility = View.VISIBLE
        }
        
        val deckButton = view.findViewById<Button>(R.id.pathwayDeckButton)
        deckButton.setOnClickListener {
            val cards = player.playerDeck
            cards.sortBy {
                it.name
            }
            adapter.addCards(cards)
            cardView.visibility = View.VISIBLE
        }

        val cardViewExit = view.findViewById<Button>(R.id.discard_deck_exit_button)
        cardViewExit.setOnClickListener {
            adapter.clearCards()
            cardView.visibility = View.INVISIBLE

        }

        setButtonListener(rooms)

        return view
    }
}
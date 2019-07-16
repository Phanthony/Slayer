package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Items.Cards.Card_Bite
import com.example.slaythebloodbourne.Entities.Items.Cards.Card_Block
import com.example.slaythebloodbourne.Entities.Items.Cards.Card_Strike
import com.example.slaythebloodbourne.Entities.Items.Item
import com.example.slaythebloodbourne.Entities.Items.Item_Potion_100
import com.example.slaythebloodbourne.Entities.Items.Item_Potion_50
import com.example.slaythebloodbourne.Modules.RecyclerViewShopAdapter
import com.example.slaythebloodbourne.R


class ShopFragment(private val player: Character) : Fragment() {

    private val adapter = RecyclerViewShopAdapter(arrayListOf(), arrayListOf(), arrayListOf())

    lateinit var playerGoldText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            randomizePotions()
            randomizeCards()
        }
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val main = activity as FullscreenActivity
        val view = inflater.inflate(R.layout.shop_fragment_layout, container, false)

        playerGoldText = view.findViewById(R.id.shopPlayerGoldText)
        updateGoldText()

        val itemList = view.findViewById<RecyclerView>(R.id.itemList)
        itemList.layoutManager = LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
        itemList.adapter = adapter

        view.findViewById<Button>(R.id.shopExitButton).setOnClickListener {
            main.backToPathway()
        }

        return view
    }

    private fun randomizePotions() {

        val onClickList = arrayListOf<OnClickListener>()
        val itemList = arrayListOf<Item>()
        val goldList = arrayListOf<Int>()

        //pick random number of potions
        for (i in (1..((1..2).random()))) {

            //select random type of potion
            when ((1..4).random()) {
                1 -> {
                    val goldCost = (85..128).random()
                    val potion = Item_Potion_100()
                    val listener = OnClickListener {
                        if (checkGold(goldCost)) {
                            player.getHealth(player.health)
                            val index = adapter.itemList.indexOf(potion)
                            adapter.deleteItem(index)
                        }
                    }
                    goldList.add(goldCost)
                    itemList.add(potion)
                    onClickList.add(listener)
                }
                else -> {
                    val goldCost = (51..72).random()
                    val potion = Item_Potion_50()
                    val listener = OnClickListener {
                        if (checkGold(goldCost)) {
                            player.getHealth(player.health / 2)
                            val index = adapter.itemList.indexOf(potion)
                            adapter.deleteItem(index)
                        }
                    }
                    goldList.add(goldCost)
                    itemList.add(potion)
                    onClickList.add(listener)
                }
            }
        }
        adapter.addItems(itemList, onClickList,goldList)
    }


    private fun randomizeCards() {
        val onClickList = arrayListOf<OnClickListener>()
        val itemList = arrayListOf<Item>()
        val goldList = arrayListOf<Int>()

        //Select random cards
        for (i in (1..(0..3).random())) {
            val goldCost = (31..54).random()
            val card = when ((1..5).random()) {
                in (1..2) -> {
                    Card_Strike(player)
                }
                in (3..4) -> {
                    Card_Block(player)
                }
                else -> Card_Bite(player)
            }
            val listener = OnClickListener {
                if (checkGold(goldCost)) {
                    player.playerDeck.add(card)
                    player.playerDeck.shuffle()
                    val index = adapter.itemList.indexOf(card)
                    adapter.deleteItem(index)
                }
            }
            goldList.add(goldCost)
            itemList.add(card)
            onClickList.add(listener)
        }
        adapter.addItems(itemList, onClickList,goldList)
    }

    private fun updateGoldText(){
        playerGoldText.text = "${player.currentGold}"
    }

    private fun checkGold(cost: Int): Boolean {
        val check = player.currentGold >= cost
        if (check) {
            player.currentGold -= cost
            updateGoldText()
        }
        return check
    }
}


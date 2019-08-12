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
import com.example.slaythebloodbourne.Entities.Items.Cards.*
import com.example.slaythebloodbourne.Entities.Items.Item
import com.example.slaythebloodbourne.Entities.Items.Item_Potion_100
import com.example.slaythebloodbourne.Entities.Items.Item_Potion_50
import com.example.slaythebloodbourne.Modules.RecyclerViewShopAdapter
import com.example.slaythebloodbourne.R

class ShopFragment(
    private val player: Character,
    var itemList: ArrayList<Item>? = null,
    var goldList: ArrayList<Int>? = null
) : Fragment() {

    val adapter = RecyclerViewShopAdapter(arrayListOf(), arrayListOf(), arrayListOf())

    lateinit var playerGoldText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        val shopTempList: ArrayList<Item>
        val goldTempList: ArrayList<Int>
        if (itemList == null) {
            shopTempList = arrayListOf()
            goldTempList = arrayListOf()
            randomizePotions(shopTempList, goldTempList)
            randomizeCards(shopTempList, goldTempList)
        } else {
            shopTempList = itemList!!
            goldTempList = goldList!!
        }
        val tempListenerList = arrayListOf<OnClickListener>()
        for (i in 0 until shopTempList.size) {
            val listener = when (i.javaClass) {
                Item_Potion_100::class.java -> {
                    OnClickListener {
                        if (checkGold(goldTempList[i])) {
                            player.getHealth(player.health)
                            val index = adapter.itemList.indexOf(shopTempList[i])
                            adapter.deleteItem(index)
                        }
                    }
                }
                Item_Potion_50::class.java -> {
                    OnClickListener {
                        if (checkGold(goldTempList[i])) {
                            player.getHealth(player.health / 2)
                            val index = adapter.itemList.indexOf(shopTempList[i])
                            adapter.deleteItem(index)
                        }
                    }
                }
                Card::class.java -> {
                    OnClickListener {
                        if (checkGold(goldTempList[i])) {
                            val card = shopTempList[i] as Card
                            player.playerDeck.add(card)
                            player.playerDeck.shuffle()
                            val index = adapter.itemList.indexOf(card)
                            adapter.deleteItem(index)
                        }
                    }
                }
                else -> {
                    OnClickListener {
                    }
                }
            }
            tempListenerList.add(listener)
        }
        adapter.addItems(shopTempList, tempListenerList, goldTempList)
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
            main.backToPathway(player)
        }
        return view
    }


    private fun randomizePotions(itemList: ArrayList<Item>, goldList: ArrayList<Int>) {

        //pick random number of potions
        for (i in (1..((1..2).random()))) {

            //select random type of potion
            when ((1..4).random()) {
                1 -> {
                    val goldCost = (85..128).random()
                    val potion = Item_Potion_100()
                    goldList.add(goldCost)
                    itemList.add(potion)
                }
                else -> {
                    val goldCost = (51..72).random()
                    val potion = Item_Potion_50()
                    goldList.add(goldCost)
                    itemList.add(potion)
                }
            }
        }
    }


    private fun randomizeCards(itemList: ArrayList<Item>, goldList: ArrayList<Int>) {
        //Select random cards
        for (i in (1..(0..3).random())) {
            val goldCost = (31..54).random()
            val card = when ((1..10).random()) {
                in (1..2) -> {
                    Card_Strike(player)
                }
                in (3..4) -> {
                    Card_Block(player)
                }
                5 -> {
                    Card_Bash(player)
                }
                6 -> {
                    Card_AttackBreak(player)
                }
                7 -> {
                    Card_ShieldBreak(player)
                }
                8 -> {
                    Card_Charge(player)
                }
                9 -> {
                    Card_RaiseAttack(player)
                }
                else -> Card_Bite(player)
            }
            goldList.add(goldCost)
            itemList.add(card)
        }
    }

    private fun updateGoldText() {
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


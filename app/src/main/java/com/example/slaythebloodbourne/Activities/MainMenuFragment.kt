package com.example.slaythebloodbourne.Activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.slaythebloodbourne.Modules.PathWayDAO
import com.example.slaythebloodbourne.Modules.PathWayDatabase
import com.example.slaythebloodbourne.Modules.PathWayEntity
import com.example.slaythebloodbourne.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.nio.file.Path

class MainMenuFragment(val pathwayDao: PathWayDAO):Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.mainscreen_fragment_layout,container,false)
        val startButton = view.findViewById<Button>(R.id.gameStartButton)

        startButton.setOnClickListener {
            checkSave()
        }

        return view
    }

    fun checkSave(){
        val mainActivity = activity as FullscreenActivity
        CoroutineScope(Dispatchers.IO).launch {
            val game: Fragment
            val previousPathway = pathwayDao.getPathway()
            game = if(previousPathway == null){
                PathwayFragment()
            } else{
                val playerWithCards = previousPathway.player.apply {
                    this.playerDiscard = previousPathway.playerDiscard
                    this.playerDeck = previousPathway.playerDeck
                    this.playerHand = previousPathway.playerHand
                }
                playerWithCards.playerDiscard.forEach { it.player = playerWithCards }
                playerWithCards.playerHand.forEach { it.player = playerWithCards }
                playerWithCards.playerDeck.forEach { it.player = playerWithCards }
                when(previousPathway.currentPosition) {
                    0 -> {
                        val enemyTable = pathwayDao.getEnemy()
                        BattleFragment(playerWithCards,previousPathway.floorCount,enemyTable.enemy,true)
                    }
                    1 -> {
                        val shopTable = pathwayDao.getShop()
                        ShopFragment(playerWithCards,shopTable.itemList,shopTable.goldList)
                    }
                    2 -> {
                        val chestTable = pathwayDao.getChest()
                        VictoryFragment(chestTable.cardReward,chestTable.goldReward,playerWithCards,true)
                    }
                    3 -> {
                        val shrineTable = pathwayDao.getShrine()
                        ShrineFragment(playerWithCards,shrineTable.reward)
                    }
                    4 -> {
                        val rewardTable = pathwayDao.getChest()
                        VictoryFragment(rewardTable.cardReward,rewardTable.goldReward,playerWithCards)
                    }
                    else -> {
                        PathwayFragment(
                            previousPathway.roomSelections,
                            previousPathway.player,
                            previousPathway.floorCount
                        )
                    }
                }
            }
            if(pathwayDao.getPathway()?.currentPosition == 1 || pathwayDao.getPathway() == null) {
                mainActivity.replaceCurrentFragmentSave(game)
            }
            else{
                mainActivity.replaceCurrentFragmentNoSave(game)
            }
        }
    }
}
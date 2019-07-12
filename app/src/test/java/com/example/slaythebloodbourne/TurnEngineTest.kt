package com.example.slaythebloodbourne

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy_Zombie
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class TurnEngineTest {

    @Test
    fun `test enemy block`(){
        val mPlayer = Character()
        mPlayer.StartGame()

        val mEnemy = Enemy_Zombie()

        val battle = TurnEngine(mPlayer,mEnemy)

        battle.startTurn()

        battle.playerDrawFromHand()
        println("Player hand is ${battle.player.playerHand}")
        battle.playCard(battle.player.playerHand.removeAt(0))
        println("Player hand is ${battle.player.playerHand}")
        battle.endTurn()

        battle.startTurn()

        battle.playerDrawFromHand()
        println("Player hand is ${battle.player.playerHand}")
        battle.playCard(battle.player.playerHand.removeAt(0))
        battle.playCard(battle.player.playerHand.removeAt(0))
        battle.playCard(battle.player.playerHand.removeAt(0))
        println("Player hand is ${battle.player.playerHand}")
        battle.endTurn()

        battle.startTurn()

        battle.playerDrawFromHand()
        println("Player hand is ${battle.player.playerHand}")
        battle.playCard(battle.player.playerHand.removeAt(0))
        battle.playCard(battle.player.playerHand.removeAt(0))
        println("Player hand is ${battle.player.playerHand}")
        battle.endTurn()

        battle.startTurn()

        battle.playerDrawFromHand()
        println("Player hand is ${battle.player.playerHand}")
        battle.playCard(battle.player.playerHand.removeAt(0))
        battle.playCard(battle.player.playerHand.removeAt(0))
        println("Player hand is ${battle.player.playerHand}")
        battle.endTurn()

        battle.startTurn()

        battle.playerDrawFromHand()
        println("Player hand is ${battle.player.playerHand}")
        battle.playCard(battle.player.playerHand.removeAt(0))
        battle.playCard(battle.player.playerHand.removeAt(0))
        println("Player hand is ${battle.player.playerHand}")
        battle.endTurn()

        battle.startTurn()

        battle.playerDrawFromHand()
        println("Player hand is ${battle.player.playerHand}")
        battle.playCard(battle.player.playerHand.removeAt(0))
        battle.playCard(battle.player.playerHand.removeAt(0))
        battle.playCard(battle.player.playerHand.removeAt(0))
        println("Player hand is ${battle.player.playerHand}")
        battle.endTurn()

        battle.startTurn()

        battle.playerDrawFromHand()
        println("Player hand is ${battle.player.playerHand}")
        battle.playCard(battle.player.playerHand.removeAt(0))
        println("Player hand is ${battle.player.playerHand}")
        battle.endTurn()
    }
}
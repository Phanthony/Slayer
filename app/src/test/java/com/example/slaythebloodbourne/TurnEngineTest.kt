package com.example.slaythebloodbourne

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy_Zombie
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.Entities.Items.Cards.Card_Strike
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


@RunWith(JUnit4::class)
class TurnEngineTest {

    @Test
    fun `test enemy block`(){
        val mPlayer = Character()
        mPlayer.playerDeck.add(Card_Strike(mPlayer))
        println(mPlayer.playerDeck[0].description)
        mPlayer.playerBonusAttack = 100
        println(mPlayer.playerDeck[0].description)
    }
}
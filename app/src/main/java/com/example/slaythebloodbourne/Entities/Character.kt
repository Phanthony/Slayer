package com.example.slaythebloodbourne.Entities

import com.example.slaythebloodbourne.Entities.Cards.Card
import com.example.slaythebloodbourne.Entities.Cards.Card_Block
import com.example.slaythebloodbourne.Entities.Cards.Card_Strike

class Character {

    var health = 100
    var playerDeck = arrayListOf<Card>()
    var playerDiscard = arrayListOf<Card>()
    var maxCards = 4
    var energy = 3

    var playerCurrentHealth = health
    var playerHand = arrayListOf<Card>()
    var playerCurrentEnergy = energy
    var playerBlock = 0


    fun addCardsToDeck(){
        for(i in 0..5){
            val baseStrike = Card_Strike()
            val baseBlock = Card_Block()
            playerDeck.add(baseBlock)
            playerDeck.add(baseStrike)
        }
        playerDeck.shuffle()
    }

    fun takeDamage(damageDone:Int){
        playerCurrentHealth -= damageDone
        if(playerCurrentHealth<0) playerCurrentHealth = 0
    }
}
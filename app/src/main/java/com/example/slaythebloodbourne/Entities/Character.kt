package com.example.slaythebloodbourne.Entities

import com.example.slaythebloodbourne.Entities.Items.Cards.*

class Character {

    //Base stats
    var health = 100
    var playerDeck = arrayListOf<Card>()
    var playerDiscard = arrayListOf<Card>()
    var maxCards = 4
    var energy = 3

    var currentGold = 0

    var playerCurrentHealth = health
    var playerHand = arrayListOf<Card>()
    var playerCurrentEnergy = energy
    var playerBlock = 0

    //Shrine Bonuses
    var playerBonusAttack = 0
    var playerBonusBlock = 0

    //Debuff stats
    var tempAttack = 0
    var tempBlock = 0


    fun addCardsToDeck(){
        for(i in 1..4){
            val baseStrike = Card_Strike(this)
            val baseBlock = Card_AttackBreak(this)
            playerDeck.add(baseBlock)
            playerDeck.add(baseStrike)
        }
        playerDeck.shuffle()
    }

    fun getHealth(int: Int){
        playerCurrentHealth += int
        if (playerCurrentHealth>health) playerCurrentHealth = health
    }

    fun takeDamage(damageDone:Int){
        playerCurrentHealth -= damageDone
        if(playerCurrentHealth<0) playerCurrentHealth = 0
    }
}
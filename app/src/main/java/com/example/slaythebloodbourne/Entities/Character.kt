package com.example.slaythebloodbourne.Entities

import com.example.slaythebloodbourne.Entities.Items.Cards.*

class Character {

    //Base stats
    var health = 100
    @Transient var playerDeck = arrayListOf<Card>()
    @Transient var playerDiscard = arrayListOf<Card>()
    var maxCards = 4
    var energy = 3

    var currentGold = 0

    var playerCurrentHealth = health
    @Transient var playerHand = arrayListOf<Card>()
    var playerCurrentEnergy = energy
    var playerBlock = 0

    //Shrine Bonuses
    var playerBonusAttack = 1
    var playerBonusBlock = 1

    //Debuff/Buff stats
    var tempAttack = 0
    var tempBlock = 0

    fun getHealth(int: Int){
        playerCurrentHealth += int
        if (playerCurrentHealth>health) playerCurrentHealth = health
    }

    fun takeDamage(damageDone:Int){
        playerCurrentHealth -= damageDone
        if(playerCurrentHealth<0) playerCurrentHealth = 0
    }

}
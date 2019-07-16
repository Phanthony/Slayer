package com.example.slaythebloodbourne.Entities.Enemies

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.R

class Enemy_Zombie(private val floor: Int): Enemy {
    override val image = R.drawable.enemy_zombie
    override var energy = 3
    override val health = 50
    override var bonusDamage = 0
    override val baseDamage = 7
    override val baseBlock = 3
    override var bonusBlock = 0

    override var enemyCurrentHealth = health
    override var enemyBlock = 0
    override var enemyCurrentEnergy = 0
    override var enemyAttack = 0

    override val specialAbility = true

    override fun ability(character: Character){
        bonusDamage += 2
        enemyCurrentHealth+=2
        if(enemyCurrentHealth>health) enemyCurrentHealth = health
    }

    override fun takeDamage(damage: Int) {
        enemyCurrentHealth -= damage
    }

    override fun chooseMove(): Move {
        val result = (0..1).random()
        return if (result == 1) Move(baseDamage + bonusDamage, 0)
        else Move(0, baseBlock + bonusBlock)
    }
}
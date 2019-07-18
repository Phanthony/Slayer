package com.example.slaythebloodbourne.Entities.Enemies

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.R

class Enemy_Zombie(private val floor: Int): Enemy {
    override val image = R.drawable.enemy_zombie
    override var energy = 3
    override val health = 50
    override var tempDamage = 0
    override val baseDamage: Int get() = (5..8).random()
    override val baseBlock: Int get() = (3..5).random()
    override var tempBlock = 0

    override var enemyCurrentHealth = health
    override var enemyBlock = 0
    override var enemyCurrentEnergy = 0
    override var enemyAttack = 0

    override val specialAbility = true

    override fun ability(character: Character){
        tempDamage += 5
        tempBlock += 5
        enemyCurrentHealth+=5
        if(enemyCurrentHealth>health) enemyCurrentHealth = health
    }

    override fun takeDamage(damage: Int) {
        enemyCurrentHealth -= damage
    }

    override fun chooseMove(): Move {
        val result = (0..1).random()
        return if (result == 1) Move(baseDamage, 0)
        else Move(0, baseBlock)
    }
}
package com.example.slaythebloodbourne.Entities.Enemies

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.R

class Enemy_Slime(val floor: Int):Enemy {
    override var tempDamage = 0
    override val baseDamage: Int
        get() = (1..10).random()
    override var tempBlock = 0
    override val baseBlock: Int
        get() = (1..10).random()
    override val health = 35
    override var energy = 3
    override var enemyCurrentHealth = health
    override var enemyBlock = 0
    override var enemyCurrentEnergy = 0
    override var enemyAttack = 0
    override val image = R.drawable.enemy_slime
    override val specialAbility = true

    override fun ability(character: Character) {
        character.tempAttack -= 2
    }

    override fun takeDamage(damage: Int) {
        enemyCurrentHealth -= damage
    }

    override fun chooseMove(): Move {
        val result = (0..2).random()
        return if (result == 1) Move(0,baseDamage)
        else Move(baseDamage, 0)
    }

}
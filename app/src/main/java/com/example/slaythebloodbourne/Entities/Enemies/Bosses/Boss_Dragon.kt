package com.example.slaythebloodbourne.Entities.Enemies.Bosses

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy
import com.example.slaythebloodbourne.Entities.Enemies.Move
import com.example.slaythebloodbourne.R

class Boss_Dragon(private val floor: Int): Enemy {
    override val image = R.drawable.boss_dragon_sign
    override var tempDamage = 0
    override val baseDamage = 10
    override var tempBlock = 0
    override val baseBlock = 3
    override val health = 150
    override var energy = 5

    override var enemyCurrentHealth = health
    override var enemyBlock = 0
    override var enemyCurrentEnergy = 0
    override var enemyAttack = 0
    override val specialAbility = true

    override fun ability(character: Character) {
        tempDamage += 5
        tempBlock += 2
    }

    override fun takeDamage(damage: Int) {
        enemyCurrentHealth -= damage
    }

    override fun chooseMove(): Move {
        val result = (0..1).random()
        return if (result == 1) Move(baseDamage + tempDamage, 0)
        else Move(0, baseBlock + tempBlock)
    }
}
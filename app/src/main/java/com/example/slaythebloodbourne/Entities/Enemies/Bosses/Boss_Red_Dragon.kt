package com.example.slaythebloodbourne.Entities.Enemies.Bosses

import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy
import com.example.slaythebloodbourne.Entities.Enemies.Move
import com.example.slaythebloodbourne.R

class Boss_Red_Dragon(val floor: Int):Enemy {
    override var tempDamage = 0
    override val baseDamage: Int
        get() = (6..10).random()
    override var tempBlock = 0
    override val baseBlock: Int
        get() = (4..8).random()
    override val health = 130
    override var energy = 4
    override var enemyCurrentHealth = health
    override var enemyBlock = 0
    override var enemyCurrentEnergy = 0
    override var enemyAttack = 0
    override val image = R.drawable.boss_red_dragon
    override val specialAbility = true

    override fun ability(character: Character) {
        tempDamage += 10
    }

    override fun takeDamage(damage: Int) {
        enemyCurrentHealth-=damage
    }

    override fun chooseMove(): Move {
        val result = (0..1).random()
        return if (result == 1) Move(baseDamage, 0)
        else Move(0, baseBlock)
    }
}
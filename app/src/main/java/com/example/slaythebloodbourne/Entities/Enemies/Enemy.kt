package com.example.slaythebloodbourne.Entities.Enemies

import com.example.slaythebloodbourne.Entities.Character

interface Enemy {
    var bonusDamage: Int
    val baseDamage: Int
    var bonusBlock: Int
    val baseBlock: Int
    val health: Int
    var energy: Int

    var enemyCurrentHealth: Int
    var enemyBlock: Int
    var enemyCurrentEnergy: Int
    var enemyAttack: Int


    val specialAbility: Boolean

    fun ability(character: Character)

    fun takeDamage(damage: Int)

    fun chooseMove(): Move
}
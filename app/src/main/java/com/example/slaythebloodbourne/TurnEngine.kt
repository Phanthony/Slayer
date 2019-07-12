package com.example.slaythebloodbourne

import com.example.slaythebloodbourne.Entities.Cards.Card
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy
import com.example.slaythebloodbourne.Entities.Enemies.Move

const val BATTLE_ONGOING = 0
const val BATTLE_LOST = 1
const val BATTLE_WON = 2

class TurnEngine(val player: Character, private val enemy: Enemy) {

    var battleState = BATTLE_ONGOING
    var enemyMove = startTurn()

    fun startTurn(): Move {
        enemyEnergyIncrease()
        val enemyMove = enemy.chooseMove()
        if (battleState == BATTLE_ONGOING) {
            enemy.enemyBlock = enemyMove.block
            enemy.enemyAttack = enemyMove.attack
            playerDrawFromHand()
        }
        return enemyMove
    }

    fun playerDrawFromHand() {
        if (battleState == BATTLE_ONGOING) {
            for (card in 1..player.maxCards) {
                if (player.playerDeck.isEmpty()) {
                    resetPlayerDeck(player.playerDiscard, player.playerDeck)
                }
                player.playerHand.add(player.playerDeck.removeAt(0))
            }
            println(player.playerHand)
        }
    }

    fun endTurn() {
        if (battleState == BATTLE_ONGOING) {
            player.playerDiscard.addAll(player.playerHand)
            val enemyDamageDone =
                if (enemy.enemyAttack - player.playerBlock > 0) enemy.enemyAttack - player.playerBlock else 0
            player.takeDamage(enemyDamageDone)
            if (player.playerCurrentHealth <= 0) {
                battleState = BATTLE_LOST; return
            }
        }
        resetTurn()
    }


    private fun resetPlayerDeck(discardPile: ArrayList<Card>, deckPile: ArrayList<Card>) {
        discardPile.shuffle()
        deckPile.addAll(discardPile)
        discardPile.clear()

    }

    private fun enemyEnergyIncrease() {
        if (enemy.specialAbility) {
            if (enemy.enemyCurrentEnergy < enemy.energy) {
                enemy.enemyCurrentEnergy++
            } else {
                enemy.enemyCurrentEnergy = 0
                enemy.ability(player)
            }
        }
    }

    fun checkPlayerEnoughEnergy(card: Card): Boolean {
        return (card.energyCost <= player.playerCurrentEnergy)
    }

    fun playCard(card: Card) {
        if (battleState == BATTLE_ONGOING) {
            player.playerCurrentEnergy -= card.energyCost
            player.playerBlock += card.block
            val playerDamageDone = if (card.attack - enemy.enemyBlock > 0) card.attack - enemy.enemyBlock else 0
            enemy.takeDamage(playerDamageDone)
            player.playerDiscard.add(card)
            if (enemy.enemyCurrentHealth <= 0) {
                battleState = BATTLE_WON
            }
        }
    }

    private fun resetTurn() {
        player.playerBlock = 0
        enemy.enemyBlock = 0
        player.playerDiscard.addAll(player.playerHand)
        player.playerHand.clear()
        player.playerCurrentEnergy = player.energy
        enemyMove = startTurn()
    }
}
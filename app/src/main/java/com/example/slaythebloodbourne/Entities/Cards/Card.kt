package com.example.slaythebloodbourne.Entities.Cards

interface Card {
    val attack: Int
    val block: Int
    val name: String
    val energyCost: Int
    val description: String
}
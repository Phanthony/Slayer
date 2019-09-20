package com.example.slaythebloodbourne.Modules

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Enemies.Enemy
import com.example.slaythebloodbourne.Entities.Enemies.Move
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.Entities.Items.Item
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class CharacterTypeConverter {

    val gson: Gson


    init {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Card::class.java, CardInterfaceAdapter())
        gsonBuilder.registerTypeAdapter(Enemy::class.java,EnemyInterfaceAdapter())
        gsonBuilder.registerTypeAdapter(Item::class.java, ItemInterfaceAdapter())
        gson = gsonBuilder.create()
    }

    @TypeConverter
    fun characterToString(character: Character): String {
        val characterType = object : TypeToken<Character>() {}.type
        return gson.toJson(character, characterType)
    }

    @TypeConverter
    fun stringToCharacter(character: String): Character {
        val characterType = object : TypeToken<Character>() {}.type
        return gson.fromJson(character, characterType)
    }

    @TypeConverter
    fun cardListToString(cardList: ArrayList<Card>): String {
        val cardType = object : TypeToken<Card>() {}.type
        var stringList: List<String> = cardList.map {
            gson.toJson(it, cardType)
        }
        return gson.toJson(stringList)
    }

    @TypeConverter
    fun stringToCardList(cardListJson: String): ArrayList<Card> {
        val stringListType = object : TypeToken<List<String>>() {}.type
        var stringList: List<String> = gson.fromJson(cardListJson, stringListType)
        var cardList = arrayListOf<Card>()
        val cardType = object : TypeToken<Card>() {}.type
        for (card in stringList) {
            cardList.add(gson.fromJson(card, cardType))
        }
        return cardList
    }

    @TypeConverter
    fun cardToString(card: Card?): String {
        val cardType = object : TypeToken<Card?>() {}.type
        return gson.toJson(card, cardType)
    }

    @TypeConverter
    fun stringToCard(card: String): Card? {
        val cardType = object : TypeToken<Card?>() {}.type
        return gson.fromJson(card, cardType)
    }

    @TypeConverter
    fun enemyToString(enemy: Enemy): String {
        val enemyType = object : TypeToken<Enemy>() {}.type
        return gson.toJson(enemy, enemyType)
    }

    @TypeConverter
    fun stringToEnemy(enemy: String): Enemy {
        val enemyType = object : TypeToken<Enemy>() {}.type
        return gson.fromJson(enemy, enemyType)
    }

    @TypeConverter
    fun moveToString(move: Move): String {
        val cardType = object : TypeToken<Card>() {}.type
        return gson.toJson(move, cardType)
    }

    @TypeConverter
    fun StringToMove(move: String): Move {
        val cardType = object : TypeToken<Card>() {}.type
        return gson.fromJson(move, cardType)
    }

    @TypeConverter
    fun itemListToString(itemList: ArrayList<Item>): String {
        var stringList: List<String> = itemList.map { itemToString(it) }
        return gson.toJson(stringList)
    }

    @TypeConverter
    fun stringToItemList(itemListJson: String): ArrayList<Item> {
        val stringListType = object : TypeToken<List<String>>() {}.type
        var stringList: List<String> = gson.fromJson(itemListJson, stringListType)
        var cardList = arrayListOf<Item>()
        for (i in stringList) {
            cardList.add(stringToItem(i))
        }
        return cardList
    }

    private fun itemToString(item: Item): String {
        val itemType = object : TypeToken<Item>() {}.type
        return gson.toJson(item, itemType)
    }

    private fun stringToItem(item: String): Item {
        val itemType = object : TypeToken<Item>() {}.type
        return gson.fromJson(item, itemType)
    }
}
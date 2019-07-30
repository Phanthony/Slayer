package com.example.slaythebloodbourne.Modules

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.slaythebloodbourne.Entities.Character
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class CharacterTypeConverter {

    val gson: Gson


    init {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(Card::class.java, CardInterfaceAdapter())
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
        var stringList: List<String> = cardList.map { cardToString(it) }
        return gson.toJson(stringList)
    }

    @TypeConverter
    fun stringToCardList(cardListJson: String): ArrayList<Card> {
        val stringListType = object : TypeToken<List<String>>() {}.type
        var stringList: List<String> = gson.fromJson(cardListJson,stringListType)
        var cardList = arrayListOf<Card>()
        for (i in stringList){
            cardList.add(StringToCard(i))
        }
        return cardList
    }

    fun cardToString(card: Card): String {
        val cardType = object : TypeToken<Card>(){}.type
        return gson.toJson(card,cardType)
    }

    fun StringToCard(card: String): Card {
        val cardType = object : TypeToken<Card>() {}.type
        return gson.fromJson(card,cardType)
    }
}
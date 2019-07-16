package com.example.slaythebloodbourne.Modules

import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.R
import kotlinx.android.synthetic.main.recyclerview_cards_layout.view.*


class RecyclerViewCardsAdapter(val cardList: ArrayList<Card>, val listenerList: ArrayList<OnClickListener>) : RecyclerView.Adapter<RecyclerViewCardsAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardButton: Button = itemView.cardButton
        val cardEnergy: TextView = itemView.cardEnergyCost
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_cards_layout,parent,false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val currentListener = listenerList[position]
        val currentCard = cardList[position]
        holder.cardButton.text = "${currentCard.name}\n\n${currentCard.description}"
        holder.cardButton.setOnClickListener(currentListener)
        holder.cardEnergy.text = "${currentCard.energyCost}"
    }

    fun addCards(cards: ArrayList<Card>, listener: ArrayList<OnClickListener>){
        cardList.addAll(cards)
        listenerList.addAll(listener)
        notifyDataSetChanged()
    }

    fun deleteCard(int: Int){
        cardList.removeAt(int)
        listenerList.removeAt(int)
        notifyDataSetChanged()
    }

    fun clearCards(){
        cardList.clear()
        listenerList.clear()
        notifyDataSetChanged()
    }

}
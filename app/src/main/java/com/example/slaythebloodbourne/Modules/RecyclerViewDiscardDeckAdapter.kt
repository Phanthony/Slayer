package com.example.slaythebloodbourne.Modules

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.R
import kotlinx.android.synthetic.main.recyclerview_cards_layout.view.*

class RecyclerViewDiscardDeckAdapter(val cardList: ArrayList<Card>) : RecyclerView.Adapter<RecyclerViewDiscardDeckAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val cardEnergy: TextView = itemView.cardEnergyCost
        val cardTitle: TextView = itemView.card_text_title
        val cardDesc: TextView = itemView.card_text_description
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_cards_discard_deck,parent,false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCard = cardList[position]
        holder.cardDesc.text = currentCard.description
        holder.cardEnergy.text = currentCard.energyCost.toString()
        holder.cardTitle.text = currentCard.name
    }

    fun addCards(cards: ArrayList<Card>){
        cardList.addAll(cards)
        notifyDataSetChanged()
    }

    fun clearCards(){
        cardList.clear()
        notifyDataSetChanged()
    }
}
package com.example.slaythebloodbourne.Modules

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.slaythebloodbourne.Entities.Items.Cards.Card
import com.example.slaythebloodbourne.Entities.Items.Item
import com.example.slaythebloodbourne.R
import kotlinx.android.synthetic.main.recyclerview_shop_layout.view.*

class RecyclerViewShopAdapter(val itemList: ArrayList<Item>,private val listenerList: ArrayList<View.OnClickListener>, val goldList: ArrayList<Int>) : RecyclerView.Adapter<RecyclerViewShopAdapter.ViewHolder>(){

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val itemButton: Button = itemView.shopItem!!
        val itemGoldCost: TextView = itemView.itemGoldCostText!!
        val itemEnergyCost: TextView = itemView.itemEnergyCostText!!
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_shop_layout,parent,false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int){
        val currentListener = listenerList[position]
        val currentItem = itemList[position]
        val goldCost = goldList[position]
        holder.itemButton.text = "${currentItem.name}\n\n${currentItem.description}"
        holder.itemButton.setOnClickListener(currentListener)
        holder.itemGoldCost.text = "$goldCost"
        if(currentItem is Card){
            holder.itemEnergyCost.text = "${currentItem.energyCost}"
        }
    }

    fun addItems(items: ArrayList<Item>,listener: ArrayList<View.OnClickListener>,gold: ArrayList<Int>){
        itemList.addAll(items)
        listenerList.addAll(listener)
        goldList.addAll(gold)
        notifyDataSetChanged()
    }

    fun deleteItem(int: Int){
        goldList.removeAt(int)
        itemList.removeAt(int)
        listenerList.removeAt(int)
        notifyDataSetChanged()
    }


}
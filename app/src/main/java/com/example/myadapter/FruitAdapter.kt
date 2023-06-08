package com.example.myadapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Fruit(val name: String, val imageId: Int)

open class FruitAdapter(val fruitList: List<Fruit>): RecyclerView.Adapter<FruitAdapter.ViewHolder>(){

    inner class ViewHolder(view:View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.img)
        val name : TextView = view.findViewById(R.id.name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fruit_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val fruit = fruitList[position]
        holder.image.setImageResource(fruit.imageId)
        holder.name.text = fruit.name
    }

    override fun getItemCount() = fruitList.size

}
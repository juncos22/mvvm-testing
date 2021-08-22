package dev.demo.mvvmtesting.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.demo.mvvmtesting.R
import dev.demo.mvvmtesting.listeners.OnItemClickListener
import dev.demo.mvvmtesting.models.Drink

class CocktailDataAdapter(private val context: Context,
                          private val drinkList: List<Drink>,
                          private val clickListener: OnItemClickListener)
    : RecyclerView.Adapter<CocktailDataAdapter.CocktailViewHolder>() {

    class CocktailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cocktailAvatar = itemView.findViewById<ImageView>(R.id.cocktailAvatar)
        val cocktailName = itemView.findViewById<TextView>(R.id.cocktailName)

        fun bind(drink: Drink, clickListener: OnItemClickListener) {
            itemView.setOnClickListener {
                clickListener.onClick(drink)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.cocktail_item, parent, false)
        return CocktailViewHolder(view)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        Glide.with(context)
            .load(drinkList[position].strDrinkThumb)
            .centerCrop()
            .placeholder(R.mipmap.ic_launcher_round)
            .into(holder.cocktailAvatar)

        holder.cocktailName.text = drinkList[position].strDrink

        holder.bind(drinkList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return drinkList.size
    }


}
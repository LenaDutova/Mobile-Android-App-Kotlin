package com.mobile.vedroid.kt.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mobile.vedroid.kt.databinding.ItemJokeBinding
import com.mobile.vedroid.kt.model.JokeAdapterModel

class JokesAdapter (val jokes: MutableList<JokeAdapterModel> = mutableListOf<JokeAdapterModel>())
    : RecyclerView.Adapter<JokesAdapter.ViewHolder>() {

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemJokeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ViewHolder (binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(jokes[position])
    }

    override fun getItemCount(): Int = jokes.size

    fun addItems(newJokes: List<JokeAdapterModel>) : List<JokeAdapterModel> {
        val addedJokes = mutableListOf<JokeAdapterModel>()
        newJokes.forEach { joke ->
            if (!jokes.contains(joke)) {
                jokes.add(joke)
                notifyItemInserted(jokes.size - 1) // todo проверить работу?
                addedJokes.add(joke)
            }
        }
//        if (addedJokes.isNotEmpty()) notifyDataSetChanged()
        return addedJokes
    }

    class ViewHolder (val binding: ItemJokeBinding):
        RecyclerView.ViewHolder (binding.root) {

        fun bindItem(item: JokeAdapterModel){
            with(binding) {
                if (item.isSingle()) {
                    itemSingleJokeText.isVisible = true
                    itemTwopartJokeSetup.isGone = true
                    itemTwopartJokeDelivery.isGone = true

                    itemSingleJokeText.text = item.getSetup()
                } else {
                    itemSingleJokeText.isGone = true
                    itemTwopartJokeSetup.isVisible = true
                    itemTwopartJokeDelivery.isVisible = true

                    itemTwopartJokeSetup.text = item.getSetup()
                    itemTwopartJokeDelivery.text = item.getDelivery()
                }
            }
        }
    }
}
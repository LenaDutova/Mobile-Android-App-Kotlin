package com.mobile.vedroid.kt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mobile.vedroid.kt.databinding.ItemJokeBinding
import com.mobile.vedroid.kt.model.DenoJoke

class DenoJokesAdapter (val jokes: MutableList<DenoJoke> = mutableListOf<DenoJoke>())
    : RecyclerView.Adapter<DenoJokesAdapter.DenoViewHolder>(), ExpandableAdapter<DenoJoke> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DenoJokesAdapter.DenoViewHolder {
        val binding = ItemJokeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return DenoJokesAdapter.DenoViewHolder (binding)
    }

    override fun onBindViewHolder(holder: DenoJokesAdapter.DenoViewHolder, position: Int) {
        holder.bindItem(jokes[position])
    }

    override fun getItemCount(): Int = jokes.size


    override fun addItems(newJokes: List<DenoJoke>) : Int {
        var count = 0
        newJokes.forEach { joke ->
            if (!jokes.contains(joke)) {
                jokes.add(index = 0, element = joke)
                count++
            }
        }

        if (count > 0) notifyDataSetChanged()
        return count
    }


    class DenoViewHolder (val binding: ItemJokeBinding):
        RecyclerView.ViewHolder (binding.root) {

        fun bindItem(item: DenoJoke){
            with(binding) {
                itemSingleJokeText.isGone = true
                itemTwopartJokeSetup.isVisible = true
                itemTwopartJokeDelivery.isVisible = true

                itemTwopartJokeSetup.text = item.setup
                itemTwopartJokeDelivery.text = item.delivery
            }
        }
    }
}
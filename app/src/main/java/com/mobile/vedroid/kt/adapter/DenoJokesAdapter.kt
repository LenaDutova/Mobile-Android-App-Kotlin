package com.mobile.vedroid.kt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        for (joke in newJokes){
            if (!jokes.contains(joke)) {
                jokes.add(0, joke);
                count++;
            }
        }
        if (count > 0) notifyDataSetChanged()
        return count
    }


    class DenoViewHolder (val binding: ItemJokeBinding):
        RecyclerView.ViewHolder (binding.root) {

        fun bindItem(item: DenoJoke){
            binding.itemSingleJokeText.visibility = View.GONE
            binding.itemTwopartJokeSetup.visibility = View.VISIBLE
            binding.itemTwopartJokeDelivery.visibility = View.VISIBLE

            binding.itemTwopartJokeSetup.text = item.setup
            binding.itemTwopartJokeDelivery.text = item.delivery
        }
    }
}
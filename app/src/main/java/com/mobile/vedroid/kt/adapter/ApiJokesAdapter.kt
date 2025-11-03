package com.mobile.vedroid.kt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.mobile.vedroid.kt.databinding.ItemJokeBinding
import com.mobile.vedroid.kt.model.ApiJoke
import com.mobile.vedroid.kt.model.DenoJoke

class ApiJokesAdapter (val jokes: MutableList<ApiJoke> = mutableListOf<ApiJoke>())
    : RecyclerView.Adapter<ApiJokesAdapter.ApiViewHolder>(),  ExpandableAdapter<ApiJoke> {

    override fun onCreateViewHolder (parent: ViewGroup, viewType: Int): ApiViewHolder {
        val binding = ItemJokeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false)
        return ApiJokesAdapter.ApiViewHolder (binding)
    }

    override fun onBindViewHolder(holder: ApiViewHolder, position: Int) {
        holder.bindItem(jokes[position])
    }

    override fun getItemCount(): Int = jokes.size


    override fun addItems(newJokes: List<ApiJoke>) : Int {
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



    class ApiViewHolder (val binding: ItemJokeBinding):
        RecyclerView.ViewHolder (binding.root) {

        fun bindItem(item: ApiJoke){
            with(binding) {
                if (item.isTypeSingle()) {
                    itemSingleJokeText.isVisible = true
                    itemTwopartJokeSetup.isGone = true
                    itemTwopartJokeDelivery.isGone = true

                    itemSingleJokeText.text = item.joke
                } else {
                    itemSingleJokeText.isGone = true
                    itemTwopartJokeSetup.isVisible = true
                    itemTwopartJokeDelivery.isVisible = true

                    itemTwopartJokeSetup.text = item.setup
                    itemTwopartJokeDelivery.text = item.delivery
                }
            }
        }
    }
}

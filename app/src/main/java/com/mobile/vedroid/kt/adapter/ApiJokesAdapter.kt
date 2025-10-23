package com.mobile.vedroid.kt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
        for (joke in newJokes){
            if (!jokes.contains(joke)) {
                jokes.add(0, joke);
                count++;
            }
        }
        if (count > 0) notifyDataSetChanged()
        return count
    }



    class ApiViewHolder (val binding: ItemJokeBinding):
        RecyclerView.ViewHolder (binding.root) {

        fun bindItem(item: ApiJoke){
            if (item.isTypeSingle()){
                binding.itemSingleJokeText.visibility = View.VISIBLE
                binding.itemTwopartJokeSetup.visibility = View.GONE
                binding.itemTwopartJokeDelivery.visibility = View.GONE

                binding.itemSingleJokeText.text = item.joke
            } else {
                binding.itemSingleJokeText.visibility = View.GONE
                binding.itemTwopartJokeSetup.visibility = View.VISIBLE
                binding.itemTwopartJokeDelivery.visibility = View.VISIBLE

                binding.itemTwopartJokeSetup.text = item.setup
                binding.itemTwopartJokeDelivery.text = item.delivery
            }

        }
    }
}

package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.SingleActivity
import com.mobile.vedroid.kt.adapter.ApiJokesAdapter
import com.mobile.vedroid.kt.adapter.DenoJokesAdapter
import com.mobile.vedroid.kt.adapter.ExpandableAdapter
import com.mobile.vedroid.kt.databinding.FragmentFinalBinding
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.model.DenoJoke
import com.mobile.vedroid.kt.network.DenoJokeService
import com.mobile.vedroid.kt.network.DenoKtorClient
import kotlinx.coroutines.launch
import java.lang.Exception

class FinalFragment : Fragment () {

    companion object{
        const val DENO_OR_API_JOKES : Boolean = true
    }

    private var _binding: FragmentFinalBinding? = null
    private val binding: FragmentFinalBinding
            get() = _binding ?: throw RuntimeException()

    lateinit var jokeAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFinalBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugging("HI")

        jokeAdapter = (if (DENO_OR_API_JOKES) DenoJokesAdapter() else ApiJokesAdapter()) as RecyclerView.Adapter<RecyclerView.ViewHolder>

        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(view.context)  // Default orientation - vertical
        binding.messagesRecyclerView.adapter = jokeAdapter

        loadJokes()

        binding.swipeToRefresh.setOnRefreshListener {
            debugging("Swiped to refreshing")
            loadJokes()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

        if (DENO_OR_API_JOKES) DenoKtorClient.close()
//        else ApiKtorClient.close() todo
    }


    private fun checkPlaceholder(){
        binding.messagesPlaceholder.visibility = if (jokeAdapter.itemCount != 0) View.GONE else View.VISIBLE
    }

    private fun loadJokes (){
        binding.swipeToRefresh.isRefreshing = true

        if (DENO_OR_API_JOKES) loadDenoJokes()
        else loadApiJokes()

        checkPlaceholder()
    }

    private fun loadDenoJokes(){
        lifecycleScope.launch {
            try {
                val list = DenoKtorClient.getJokes()
                val count: Int = (jokeAdapter as ExpandableAdapter<DenoJoke>).addItems(list)

                debugging("loading $count jokes")

                checkPlaceholder()
            } catch (e: Exception){
                debugging("Some error: ${e.message}")

                (activity as SingleActivity).showSnackBar(getString(R.string.no_internet))
            }

            binding.swipeToRefresh.isRefreshing = false
        }
    }

    private fun  loadApiJokes(){

    }
}
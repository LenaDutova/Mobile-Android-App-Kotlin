package com.mobile.vedroid.kt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
import com.mobile.vedroid.kt.model.ApiJoke
import com.mobile.vedroid.kt.model.DenoJoke
import com.mobile.vedroid.kt.network.ApiKtorClient
import com.mobile.vedroid.kt.network.DenoKtorClient
import com.mobile.vedroid.kt.network.JokeService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FinalFragment : Fragment () {

    companion object {
        const val DENO_OR_API_JOKES : Boolean = false
    }

    private var _binding: FragmentFinalBinding? = null
    private val binding: FragmentFinalBinding get() = _binding!!

    private var _jokeAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>? = null
    private val jokeAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> get() = _jokeAdapter!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFinalBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugging("HI")

        _jokeAdapter = (
                if (DENO_OR_API_JOKES) DenoJokesAdapter()
                else ApiJokesAdapter()
                ) as RecyclerView.Adapter<RecyclerView.ViewHolder>

        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(view.context)  // Default orientation - vertical
        binding.messagesRecyclerView.adapter = jokeAdapter
        loadJokes()

        binding.swipeToRefresh.setOnRefreshListener {
            debugging("Swiped to refreshing")
            loadJokes()
        }
    }

    override fun onDestroyView() {
//        // only if this fragment is the only one place for internet loading/uploading
//        if (DENO_OR_API_JOKES) DenoKtorClient.close()
//        else ApiKtorClient.close()

        _binding = null
        super.onDestroyView()
    }


    private fun checkPlaceholder(){
        binding.messagesPlaceholder.isVisible = jokeAdapter.itemCount == 0
    }

    private fun loadJokes (){
        binding.swipeToRefresh.isRefreshing = true

        if (DENO_OR_API_JOKES) loadDenoJokes()
        else loadApiJokes()
    }

    private fun loadDenoJokes(){
        lifecycleScope.launch (Dispatchers.IO) {
            try {
                val list = DenoKtorClient.getJokes()
                launch(Dispatchers.Main){
                    val count = (jokeAdapter as ExpandableAdapter<DenoJoke>).addItems(list)
                    binding.swipeToRefresh.isRefreshing = false
                    checkPlaceholder()
                    debugging("show in ${Thread.currentThread().name} $count jokes")
                }
            } catch (e: Exception){
                debugging("Some error: ${e.message}")
                launch(Dispatchers.Main) {
                    binding.swipeToRefresh.isRefreshing = false
                    (activity as SingleActivity).showSnackBar(getString(R.string.text_no_internet))
                }
            }
        }
    }

    private fun  loadApiJokes(){
        lifecycleScope.launch{
            runCatching {
                ApiKtorClient.getJokes()
            }.onSuccess { jokes ->
                val count = (jokeAdapter as ExpandableAdapter<ApiJoke>).addItems(jokes)

                debugging("show in ${Thread.currentThread().name} $count jokes")
                binding.swipeToRefresh.isRefreshing = false
                checkPlaceholder()
            }.onFailure { throwable ->
                debugging("Some error: ${throwable.message}")
                binding.swipeToRefresh.isRefreshing = false
                (requireActivity() as SingleActivity).showSnackBar(getString(R.string.text_no_internet))
            }
        }

    }
}
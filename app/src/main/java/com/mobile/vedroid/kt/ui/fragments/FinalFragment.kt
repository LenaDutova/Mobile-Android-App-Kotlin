package com.mobile.vedroid.kt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.ui.SingleActivity
import com.mobile.vedroid.kt.ui.adapter.JokesAdapter
import com.mobile.vedroid.kt.databinding.FragmentFinalBinding
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.model.JokeAdapterModel
import com.mobile.vedroid.kt.network.ApiKtorClient
import com.mobile.vedroid.kt.network.DenoKtorClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FinalFragment : Fragment() {

    companion object {
        const val DENO_OR_API_JOKES : Boolean = false
    }

    private var _binding: FragmentFinalBinding? = null
    private val binding: FragmentFinalBinding get() = _binding!!

    private var jokeAdapter: JokesAdapter = JokesAdapter()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFinalBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugging("HI")

        binding.messagesRecyclerView.layoutManager = LinearLayoutManager(view.context)  // Default orientation - vertical
        binding.messagesRecyclerView.adapter = jokeAdapter

        binding.swipeToRefresh.setOnRefreshListener {
            debugging("Swiped to refreshing")
            loadJokes()
        }

        // TODO load saved jokes or load new
        loadJokes()
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

    private fun showJokes(newJokes: List<JokeAdapterModel>){
        val addedJokes = jokeAdapter.addItems(newJokes)

        if (addedJokes.isNotEmpty()){
            debugging("show in ${Thread.currentThread().name} ${addedJokes.size} jokes")
            checkPlaceholder()

            // TODO save new here

        }
    }

    private fun loadDenoJokes(){
        lifecycleScope.launch (Dispatchers.IO) {
            try {
                val jokes = DenoKtorClient.getJokes()
                launch(Dispatchers.Main){
                    showJokes(jokes)
                }
            } catch (e: Exception){
                debugging("Some error: ${e.message}")
                launch(Dispatchers.Main) {
                    (activity as SingleActivity).showSnackBar(getString(R.string.warning_text_no_internet))
                }
            } finally {
                binding.swipeToRefresh.isRefreshing = false
            }
        }
    }

    private fun  loadApiJokes(){
        lifecycleScope.launch{
            runCatching {
                ApiKtorClient.getJokes()
            }.onSuccess { jokes ->
                showJokes(jokes)
            }.onFailure { throwable ->
                debugging("Some error: ${throwable.message}")
                (requireActivity() as SingleActivity).showSnackBar(getString(R.string.warning_text_no_internet))
            }.also {
                binding.swipeToRefresh.isRefreshing = false
            }
        }

    }
}
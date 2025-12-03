package com.mobile.vedroid.kt.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.datastore.core.FileStorage
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.databinding.FragmentFinalBinding
import com.mobile.vedroid.kt.extensions.debugging
import com.mobile.vedroid.kt.model.JokeAdapterModel
import com.mobile.vedroid.kt.network.ApiKtorClient
import com.mobile.vedroid.kt.network.DenoKtorClient
import com.mobile.vedroid.kt.storage.FileManager
import com.mobile.vedroid.kt.storage.OfflineStorage
import com.mobile.vedroid.kt.storage.sqlite.SQLiteManager
import com.mobile.vedroid.kt.ui.SingleActivity
import com.mobile.vedroid.kt.ui.adapter.JokesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.launch

class FinalFragment : Fragment() {

    private var _binding: FragmentFinalBinding? = null
    private val binding: FragmentFinalBinding get() = _binding!!

    private var jokeAdapter: JokesAdapter = JokesAdapter()

    private var _storage: OfflineStorage? = null
    private val storage: OfflineStorage get() = _storage!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFinalBinding.inflate(inflater, container, false)
        _storage = if (FILE_OR_DB_STORAGE) FileManager() else SQLiteManager()
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugging("HI")

        binding.messagesRecyclerView.adapter = jokeAdapter

        binding.swipeToRefresh.setOnRefreshListener {
            debugging("Swiped to refreshing")
            loadJokes()
        }

        // load saved jokes or load new
        lifecycleScope.launch {
            storage.load()
                .take(1)
                .collect { jokes ->
                if (jokes.isEmpty()) {
                    debugging("No saved jokes, need downloading")
                    loadJokes()
                } else {
                    debugging("Load jokes from storage: ${jokes.size}")
                    jokeAdapter.addItems(jokes)
                    checkPlaceholder()
                }
            }
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

    private fun showJokes(newJokes: List<JokeAdapterModel>){
        val addedJokes = jokeAdapter.addItems(newJokes)

        if (addedJokes.isNotEmpty()){
            checkPlaceholder()

            // save new here
            lifecycleScope.launch {
                storage.save(addedJokes)
                debugging("Save ${addedJokes.size} new jokes")
            }
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

    private fun loadApiJokes(){
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



    companion object {
        const val DENO_OR_API_JOKES : Boolean = false
        const val FILE_OR_DB_STORAGE : Boolean = true
    }
}
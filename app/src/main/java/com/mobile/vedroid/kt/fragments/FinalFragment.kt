package com.mobile.vedroid.kt.fragments

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.databinding.FragmentFinalBinding
import com.mobile.vedroid.kt.databinding.ItemMessageBinding
import com.mobile.vedroid.kt.extensions.debugging

class FinalFragment : Fragment (/*R.layout.fragment_final*/) {

    private var _binding: FragmentFinalBinding? = null
    private val binding: FragmentFinalBinding get() = _binding!!

    override fun onCreateView (inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        _binding = FragmentFinalBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        debugging("HI")

        val messageAdapter = MessageAdapter(getMockMessages())  // Create and add data adapter
        binding.messagesRecyclerView.adapter = messageAdapter
        binding.messagesRecyclerView.scrollToPosition(messageAdapter.getItemCount() - 1)    // Scroll to last

        if (messageAdapter.itemCount > 0){  // hide alert if has data
            binding.messagesPlaceholder.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    /**
     * Create some text list from "Lorem ipsum..."
     */
    private fun getMockMessages (): MutableList<String> {
        val messages : MutableList<String> = mutableListOf()

        var lorem = getString(R.string.lorem_ipsum)
        while (lorem.indexOf('.') != -1) {
            val text = lorem.substring(0, lorem.indexOf('.'))
            lorem = lorem.substring(lorem.indexOf('.') + 1)
            messages.add(text);
        }

        messages.addAll(messages)
        messages.addAll(messages)
        return messages
    }

    class MessageAdapter (var messages: List<String> = listOf()) : RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

        class ViewHolder(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
            fun bindItem(item: String){
                binding.itemMessageText.text = item
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = ItemMessageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItem(messages.get(position))
        }

        override fun getItemCount(): Int = messages.size
    }
}
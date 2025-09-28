package com.mobile.vedroid.kt.fragments

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobile.vedroid.kt.R
import com.mobile.vedroid.kt.extensions.debugging

class FinalFragment : Fragment (R.layout.fragment_final) {

    lateinit var placeholder: TextView
    lateinit var recyclerView : RecyclerView
    lateinit var messageAdapter : MessageAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debugging("HI")

        recyclerView = view.findViewById(R.id.rv_messages)
        recyclerView.layoutManager = LinearLayoutManager(view.context)  // Default orientation - vertical

        messageAdapter = MessageAdapter(getMockMessages())  // Create and add data adapter
        recyclerView.adapter = messageAdapter
//        recyclerView.scrollToPosition(0)    // As default, scroll to beginning
        recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1)    // Scroll to last

        placeholder = view.findViewById<TextView>(R.id.rv_placeholder)
        if (messageAdapter.itemCount > 0){  // hide alert if has data
            placeholder.visibility = View.GONE
        }
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

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            // Find views on layout
            val message: TextView = view.findViewById(R.id.item_message_text)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // Specify layout for items
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            // Show data from position item on layout views
            holder.message.text = messages.get(position)
        }

        override fun getItemCount(): Int = messages.size
    }
}
package com.mobile.vedroid.kt.fragments

import android.content.Context
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
        super.onViewCreated(view, savedInstanceState)
        debugging("HI")

        placeholder = view.findViewById<TextView>(R.id.rv_placeholder)

        messageAdapter = MessageAdapter(view.context)
        messageAdapter.messages = getMockMessages()

        recyclerView = view.findViewById(R.id.rv_messages)
        recyclerView.layoutManager = LinearLayoutManager(view.context)
        recyclerView.adapter = messageAdapter
        recyclerView.scrollToPosition(messageAdapter.getItemCount() - 1)

        if (messageAdapter.itemCount > 0){
            placeholder.visibility = View.GONE
        }
    }

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

    class MessageAdapter (val context: Context, var messages: List<String> = listOf()) :
        RecyclerView.Adapter<MessageAdapter.ViewHolder>(){

        class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val message: TextView = view.findViewById(R.id.item_message_text)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_message, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.message.text = messages.get(position)
        }

        override fun getItemCount(): Int = messages.size
    }
}
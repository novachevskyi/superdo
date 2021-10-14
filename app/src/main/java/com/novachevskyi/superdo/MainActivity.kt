package com.novachevskyi.superdo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), MyRecyclerViewAdapter.ItemClickListener {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = MyRecyclerViewAdapter(this, emptyList())
        adapter.setClickListener(this)
        recyclerView.adapter = adapter

        val service = WebSocketService()
        service.listenSocket {
            adapter.addItem(it)
            recyclerView.smoothScrollToPosition(0)
        }
    }

    override fun onItemClick(view: View?, position: Int) {

    }
}

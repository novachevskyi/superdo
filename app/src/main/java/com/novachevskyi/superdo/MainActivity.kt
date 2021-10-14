package com.novachevskyi.superdo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity(), RecyclerViewAdapter.ItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter

    private lateinit var connectButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        initConnection()
    }

    private fun initConnection() {
        val service = WebSocketService()
        connectButton = findViewById(R.id.connect)
        var isConnectState = true
        connectButton.setOnClickListener {
            if (isConnectState) {
                connectButton.setText(R.string.connecting)
                connectButton.isEnabled = false

                service.listenSocket({
                    GlobalScope.launch {
                        withContext(Dispatchers.Main) {
                            connectButton.isEnabled = true
                            connectButton.setText(
                                if (it) {
                                    isConnectState = false
                                    R.string.disconnect
                                } else {
                                    isConnectState = true
                                    R.string.connect
                                }
                            )
                        }
                    }
                }) {
                    adapter.addItem(it)
                    recyclerView.smoothScrollToPosition(0)
                }
            } else {
                service.disconnect()
                isConnectState = true
                connectButton.setText(R.string.connect)
            }
        }
    }

    private fun initRecyclerView() {
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val manager = LinearLayoutManager(this)
        recyclerView.layoutManager = manager
        adapter = RecyclerViewAdapter(this, emptyList())
        adapter.setClickListener(this)
        recyclerView.adapter = adapter
    }

    override fun onItemClick(view: View?, position: Int) {

    }
}

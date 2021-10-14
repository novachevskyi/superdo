package com.novachevskyi.superdo

import com.google.gson.Gson
import com.neovisionaries.ws.client.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WebSocketService {

    fun listenSocket(dataCallback: (DataModel) -> Unit) {
        val ws = WebSocketFactory().createSocket("ws://superdo-groceries.herokuapp.com/receive", 5000)
        ws.addListener(object: WebSocketAdapter() {
            override fun onTextMessage(websocket: WebSocket, message: String) {
                super.onTextMessage(websocket, message)
                val dataModel = Gson().fromJson(message, DataModel::class.java)
                dataCallback.invoke(dataModel)
            }
        })

        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    ws.connect()
                } catch (e: OpeningHandshakeException) {
                } catch (e: HostnameUnverifiedException) {
                } catch (e: WebSocketException) {
                }
            }
        }
    }
}

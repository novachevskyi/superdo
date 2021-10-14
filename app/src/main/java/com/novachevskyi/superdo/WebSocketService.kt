package com.novachevskyi.superdo

import com.google.gson.Gson
import com.neovisionaries.ws.client.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WebSocketService {

    private var webSocket: WebSocket? = null

    private var isDisconnecting = false

    fun listenSocket(connectedCallback: (Boolean) -> Unit, dataCallback: (DataModel) -> Unit) {
        webSocket = WebSocketFactory().createSocket(URL, TIMEOUT).apply {
            addListener(object: WebSocketAdapter() {
                override fun onTextMessage(websocket: WebSocket, message: String) {
                    super.onTextMessage(websocket, message)
                    if (!isDisconnecting) {
                        val dataModel = Gson().fromJson(message, DataModel::class.java)
                        dataCallback.invoke(dataModel)
                    }
                }

                override fun onConnected(
                    websocket: WebSocket?,
                    headers: MutableMap<String, MutableList<String>>?
                ) {
                    super.onConnected(websocket, headers)
                    connectedCallback.invoke(true)
                }
            })

            GlobalScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        isDisconnecting = false
                        connect()
                    } catch (e: OpeningHandshakeException) {
                        connectedCallback.invoke(false)
                    } catch (e: HostnameUnverifiedException) {
                        connectedCallback.invoke(false)
                    } catch (e: WebSocketException) {
                        connectedCallback.invoke(false)
                    }
                }
            }
        }
    }

    fun disconnect() {
        GlobalScope.launch {
            withContext(Dispatchers.IO) {
                isDisconnecting = true
                webSocket?.disconnect()
            }
        }
    }

    companion object {
        const val URL = "ws://superdo-groceries.herokuapp.com/receive"

        const val TIMEOUT = 5000
    }
}

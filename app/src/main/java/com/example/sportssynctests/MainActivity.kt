package com.example.sportssynctests

import android.app.SharedElementCallback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectToChatAPI()
    }

    private fun connectToChatAPI() {
        val socket = IO.socket("http://192.168.122.1:4000")
        socket.on(Socket.EVENT_CONNECT, Emitter.Listener {
            val usernameString = "AndroidApp"
            socket.emit("username", usernameString)
            socket.emit("chat_message", "Hello Socket!")
        })

        socket.connect()
    }
}


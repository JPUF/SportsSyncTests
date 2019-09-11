package com.example.sportssynctests

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.text.bold
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    private val socket = IO.socket("http://192.168.122.1:4000")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connectToChatAPI()

        val sendButton = findViewById<Button>(R.id.sendButton)
        sendButton.setOnClickListener { sendMessage() }
    }

    private fun connectToChatAPI() {

        socket.on(Socket.EVENT_CONNECT) {
            val usernameString = "AndroidApp"
            socket.emit("username", usernameString)
            socket.emit("chat_message", "Hello Socket!")
        }

        socket.on("chat_message") { args ->
            val msgObject = args[0] as JSONObject
            val username: String = msgObject.get("username") as String
            val message: String = msgObject.get("message") as String

            val formattedString = SpannableStringBuilder()
                .bold { append("$username: ") }
                .append(message)

            displayMessage(formattedString)
        }

        socket.connect()
    }

    private fun displayMessage(message: SpannableStringBuilder) {
        runOnUiThread {
            val recentTV = findViewById<TextView>(R.id.recentText)
            recentTV.text = message
        }
    }

    private fun sendMessage() {
        val chatEntry = findViewById<EditText>(R.id.chatEntry)
        socket.emit("chat_message", chatEntry.text)
    }
}


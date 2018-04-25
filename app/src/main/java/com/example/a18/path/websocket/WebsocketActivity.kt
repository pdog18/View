package com.example.a18.path.websocket

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.a18.path.R
import kotlinx.android.synthetic.main.activity_websocket.*
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.OutputStream

class WebsocketActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_websocket)

        btn.setOnClickListener {
            someThingError()
            someThingError()
            someThingError()
        }
    }


    var someThingError: () -> Unit = {
        Timber.d("bug")
        if (true) {     //修复bug
            someThingError = {
                Timber.d("fix")
            }
        }
    }

    fun connect() {

        val listener = EchoWebSocketListener()
        val request = Request.Builder()
            .url("ws://123.207.167.163:9010/ajaxchattest")
            .build();
        val client = OkHttpClient()
        client.newWebSocket(request, listener)

        client.dispatcher().executorService().shutdown()
        log("aaa")
        System.out
    }

    fun log(tag: String)
        = fun(target: OutputStream)
        = fun(message: Any?)
            = target.write("[$tag] $message\n".toByteArray())
}

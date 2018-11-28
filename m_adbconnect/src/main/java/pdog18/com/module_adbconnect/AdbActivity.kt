package pdog18.com.module_adbconnect

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView

class AdbActivity : AppCompatActivity() {

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adb)
        val textView = findViewById<TextView>(R.id.tv)

        //获取wifi服务
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = true
        }
        val wifiInfo = wifiManager.connectionInfo
        val ipAddress = wifiInfo.ipAddress
        val ip = intToIp(ipAddress)


        val s = "1. adb devices // 查看链接 \n" +
            "2. adb tcpip 8888  // 设置端口号 \n" +
            "3. adb connect " + ip + ":8888//连接 \n"

        textView.text = s

    }

    private fun intToIp(i: Int): String {

        return (i and 0xFF).toString() + "." +
            (i shr 8 and 0xFF) + "." +
            (i shr 16 and 0xFF) + "." +
            (i shr 24 and 0xFF)
    }
}

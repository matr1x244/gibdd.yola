package com.geekbrains.gibddyola.utils.updates

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL

class ReceiveServerAppData {

    fun getVersion(): String {
        var text = ""
        try {
            val url = URL(UpdateData.versionUrl())
            val buffer = BufferedReader(InputStreamReader(url.openStream()))
            buffer.readLine().let {
                text = it
            }
            buffer.close()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
        } catch (d: IOException) {
            d.printStackTrace()
        }
        println("@@@" + text)
        return text
    }
}
package com.geekbrains.gibddyola.utils.updates

import java.io.BufferedInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class ReceiveServerAppApk {

    fun downloadFile(): String {

        try {
            val url = URL(UpdateData.apkUrl())
            val file = "${UpdateData.downloadPath()}/${UpdateData.fileName()}"

            val stream = url.openStream()

            stream.use { inpStr ->
                BufferedInputStream(inpStr).use { buffInpStr ->
                    FileOutputStream(file).use { fileOutStr ->
                        val data = ByteArray(1024)
                        var count: Int
                        while (buffInpStr.read(data, 0, 1024).also {
                                count = it
                            } != -1) {
                            fileOutStr.write(data, 0, count)
                        }
                    }
                }
            }
            stream.close()
            return UpdateData.downloadSuccess()
        } catch (c: FileNotFoundException) {
            c.printStackTrace()
            return UpdateData.fileNotFound()
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return UpdateData.downloadError()
        } catch (d: IOException) {
            d.printStackTrace()
            return UpdateData.ioError()
        }
    }
}
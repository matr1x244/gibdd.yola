package com.geekbrains.gibddyola.utils.updates

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.utils.io.*
import io.ktor.utils.io.core.*
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.io.File
import kotlin.math.roundToInt

suspend fun downloadFile(file: File, url: String): Flow<DownloadStatus> = callbackFlow {
    try {
        val client = HttpClient(Android)
        client.prepareGet(url).execute { httpResponse ->
            if (httpResponse.status.isSuccess()) {
                val channel: ByteReadChannel = httpResponse.body()
                while (!channel.isClosedForRead) {
                    val packet = channel.readRemaining(DEFAULT_BUFFER_SIZE.toLong())
                    while (!packet.isEmpty) {
                        val bytes = packet.readBytes()
                        file.appendBytes(bytes)
                        val progress =
                            ((file.length().toDouble()
                                    / httpResponse.contentLength()!!.toDouble())
                                    * 100).roundToInt()
                        trySend(DownloadStatus.Progress(progress))
                    }
                }
                trySend(DownloadStatus.Success)
            } else {
                trySend(DownloadStatus.Error(UpdateData.downloadError()))
            }
        }
    } catch (e: TimeoutCancellationException) {
        trySend(DownloadStatus.Error(UpdateData.timeOutConnection()))
    } catch (t: Throwable) {
        trySend(DownloadStatus.Error(UpdateData.connectionError()))
    }
    cancel()
}
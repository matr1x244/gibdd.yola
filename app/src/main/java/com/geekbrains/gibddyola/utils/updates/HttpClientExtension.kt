package com.geekbrains.gibddyola.utils.updates

import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.math.roundToInt

suspend fun HttpClient.downloadFile(file: File, url: String): Flow<DownloadStatus> = callbackFlow {
    try {
        val client = HttpClient(Android)
        val response: HttpResponse = client.get(url) {
            onDownload { bytesSentTotal, contentLength ->
                val progress = (bytesSentTotal * 100f / contentLength).roundToInt()
                trySend(DownloadStatus.Progress(progress))
            }
        }
        if (response.status.isSuccess()) {
            val responseBody: ByteArray = response.readBytes()
            withContext(Dispatchers.IO) {
                file.writeBytes(responseBody)
            }
            trySend(DownloadStatus.Success)
        } else {
            trySend(DownloadStatus.Error(UpdateData.downloadError()))
        }
    } catch (e: TimeoutCancellationException) {
        trySend(DownloadStatus.Error(UpdateData.timeOutConnection()))
    } catch (t: Throwable) {
        trySend(DownloadStatus.Error(UpdateData.connectionError()))
    }
    cancel()
}
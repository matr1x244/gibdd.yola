package com.geekbrains.gibddyola.utils.updates

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

class ReceiveServerAppApk {
//    var request: DownloadManager.Request = DownloadManager.Request(
//        Uri.parse("https://basik.ru/images/landscapes_walls/08_landscape.g")
//    )
//        .setTitle("ФАЙЛ НОВЫЙ")
//        .setDescription("КАЧАЕМ АВАРКОМ")
//        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
//        .setAllowedOverMetered(true)
//
//    val downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
//    var long = 0L
//    long = downloadManager.enqueue(request)
//
//    var br = object : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            var id: Long? = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
//            if (id == long) {
//                Toast.makeText(context, "ЗАГРУЖЕН", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(context, "НЕ ЗАГРУЖЕН", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
//    context?.registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
}
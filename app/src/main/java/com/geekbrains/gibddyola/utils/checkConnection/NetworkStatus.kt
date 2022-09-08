package com.geekbrains.gibddyola.utils.checkConnection

sealed class NetworkStatus {
    object Available : NetworkStatus()
    object Unavailable: NetworkStatus()
    object Lost: NetworkStatus()
}

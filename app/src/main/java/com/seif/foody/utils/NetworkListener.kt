package com.seif.foody.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import kotlinx.coroutines.flow.MutableStateFlow

class NetworkListener : ConnectivityManager.NetworkCallback() {
    private val isNetworkAvailable = MutableStateFlow(false)

    fun checkNetworkAvailability(context: Context): MutableStateFlow<Boolean> {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerDefaultNetworkCallback(this)
        var isConnected = false

//        connectivityManager.allNetworks.forEach { network ->
//            val networkCapability = connectivityManager.getNetworkCapabilities(network)
//            networkCapability?.let {
//                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) { // if our device has internet connection
//                    isConnected = true
//                    return@forEach
//                }
//            }
//        }
        /** try this instead of above code **/
        val activeNetwork = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        capabilities?.let {
            isConnected =
                when {
                    it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) -> true
                    else -> false
                }
        }

        isNetworkAvailable.value = isConnected
        return isNetworkAvailable
    }

    override fun onAvailable(network: Network) {
        isNetworkAvailable.value = true
    }

    override fun onLost(network: Network) {
        isNetworkAvailable.value = false
    }
}
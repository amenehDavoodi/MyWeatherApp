package com.example.myweatherapp.data.location

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myweatherapp.domain.location.LocationTracker
import com.google.android.gms.location.FusedLocationProviderClient
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resume

class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application

) : LocationTracker {
    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFindLocationPermission = ContextCompat.checkSelfPermission(
            application,Manifest.permission.ACCESS_FINE_LOCATION
        )==PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,Manifest.permission.ACCESS_COARSE_LOCATION
        )==PackageManager.PERMISSION_GRANTED
       val locationManager=application.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val isGpsEnabled=locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)||
                locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (!hasAccessFindLocationPermission ||!hasAccessCoarseLocationPermission || !isGpsEnabled)
        {
             getPermission()
        }
        return suspendCancellableCoroutine {cont->
            locationClient.lastLocation.apply {
                if (isComplete)
                {
                    if (isSuccessful)
                    {
                     cont.resume(result)
                    }else{
                        cont.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener{
                    cont.resume(it)
                }
                addOnFailureListener{
                    cont.resume(null)
                }
                addOnCanceledListener {
                    cont.cancel()
                }
            }
        }
    }

    private fun getPermission() {

        ActivityCompat.requestPermissions(
//            activity,
            application.applicationContext as Activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_REQUEST_ACCESS_FINE_LOCATION)
//        return
    }
    companion object {
        private const val PERMISSION_REQUEST_ACCESS_FINE_LOCATION = 100
    }
}

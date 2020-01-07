package org.sic4change.chuqabp.course.data

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import org.sic4change.data.source.LocationDataSource


class PlayServicesLocationDataSource(application: Application) : LocationDataSource {

    private val geocoder = Geocoder(application)
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override suspend fun findLastRegion(): String? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result.toRegion())
                }
        }

    @SuppressLint("MissingPermission")
    override suspend fun findLastLatitude(): Double? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result.toLatitude())
                }
        }

    @SuppressLint("MissingPermission")
    override suspend fun findLastLongitude(): Double? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result.toLongitude())
                }
        }

    private fun Location?.toRegion(): String? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.locality + ", " + addresses?.firstOrNull()?.subAdminArea + ", " + addresses?.firstOrNull()?.adminArea + ", " + addresses?.firstOrNull()?.countryName
    }

    private fun Location?.toLatitude(): Double? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.latitude
    }

    private fun Location?.toLongitude(): Double? {
        val addresses = this?.let {
            geocoder.getFromLocation(latitude, longitude, 1)
        }
        return addresses?.firstOrNull()?.longitude
    }

}

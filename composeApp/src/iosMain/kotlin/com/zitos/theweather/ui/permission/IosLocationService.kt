package com.zitos.theweather.ui.permission

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import platform.CoreLocation.CLAuthorizationStatus
import platform.CoreLocation.CLLocationManager
import platform.CoreLocation.CLLocationManagerDelegateProtocol
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedAlways
import platform.CoreLocation.kCLAuthorizationStatusAuthorizedWhenInUse
import platform.CoreLocation.kCLAuthorizationStatusNotDetermined
import platform.darwin.NSObject

class IosLocationService : LocationService {

    private val locationManager = CLLocationManager()

    override fun isPermissionGranted(): Boolean {
        val status = locationManager.authorizationStatus()
        return status == kCLAuthorizationStatusAuthorizedAlways || status == kCLAuthorizationStatusAuthorizedWhenInUse
    }

    override fun requestLocationPermission(granted: (Boolean) -> Unit) {
        val status = locationManager.authorizationStatus()
        if (status == kCLAuthorizationStatusNotDetermined) {
            locationManager.delegate = LocationDelegate(granted)
            locationManager.requestWhenInUseAuthorization()
        } else {
            granted(true)
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    fun getLocation(): Location? {
        return locationManager.location?.coordinate?.useContents {
            Location(latitude, longitude)
        }
    }

    private class LocationDelegate(
        private val granted: (Boolean) -> Unit,
    ) : NSObject(), CLLocationManagerDelegateProtocol {

        override fun locationManager(
            manager: CLLocationManager,
            didChangeAuthorizationStatus: CLAuthorizationStatus
        ) {
            when (didChangeAuthorizationStatus) {
                kCLAuthorizationStatusAuthorizedAlways, kCLAuthorizationStatusAuthorizedWhenInUse -> {
                    granted(true)
                }

                else -> {
                    granted(false)
                }
            }
        }
    }
}

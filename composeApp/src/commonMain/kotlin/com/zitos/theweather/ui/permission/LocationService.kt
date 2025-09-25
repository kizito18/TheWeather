package com.zitos.theweather.ui.permission

interface LocationService {

    fun isPermissionGranted(): Boolean

    fun requestLocationPermission(granted: (Boolean) -> Unit)

}

data class Location(val latitude: Double, val longitude: Double)




//// commonMain
//expect class PermissionVerification() {
//    var isPermissionApproved: Boolean
//    fun requestPermission(onResult: (Boolean) -> Unit)
//}

package com.zitos.theweather.data.mappers


import platform.Foundation.NSString
import platform.Foundation.stringWithFormat

actual fun formatFloatUpToTwoDecimalPlaces(float: Float): String {
    return NSString.stringWithFormat("%.2f", float)

}
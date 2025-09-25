package com.zitos.theweather

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
package com.vickbt.bridge

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
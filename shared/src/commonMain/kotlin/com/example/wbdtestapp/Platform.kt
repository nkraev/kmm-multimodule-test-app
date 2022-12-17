package com.example.wbdtestapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
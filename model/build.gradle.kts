plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
}

android {
    namespace = "com.example.wbdtestapp.model"
    compileSdk = 33
}
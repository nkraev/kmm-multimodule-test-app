plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("plugin.serialization") version "1.7.20"
}

val ktorVersion = "2.2.1"

kotlin {
    android()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                api(project(":model"))
                api("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
    }
}

android {
    namespace = "com.example.wbdtestapp.network"
    compileSdk = 33
}
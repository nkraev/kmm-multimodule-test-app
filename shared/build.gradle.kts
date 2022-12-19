plugins {
    kotlin("multiplatform")
    id("com.android.library")
    kotlin("native.cocoapods")
    id("com.rickclephas.kmp.nativecoroutines").version("0.13.1")
}

kotlin.cocoapods {
    // Required properties
    // Specify the required Pod version here. Otherwise, the Gradle project version is used.
    version = "1.0"
    summary = "Some description for a Kotlin/Native module"
    homepage = "Link to a Kotlin/Native module homepage"

    ios.deploymentTarget = "16.2"

    // Optional properties
    // Configure the Pod name here instead of changing the Gradle project name
    name = "Shared"

    framework {
        // Required properties
        // Framework name configuration. Use this property instead of deprecated 'frameworkName'
        baseName = "shared"

        // Optional properties
        // Dynamic framework support
        isStatic = false
        linkerOpts += "-lsqlite3"
    }
}

kotlin {
    android()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(project(":network"))
                api(project(":model"))
                api(project(":database"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
                implementation("com.squareup.sqldelight:coroutines-extensions:1.5.3")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    namespace = "com.example.wbdtestapp"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
        targetSdk = 33
    }
}
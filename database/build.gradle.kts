plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("com.squareup.sqldelight")
}

val sqlDelightVersion = "1.5.3"

kotlin {
    android()

    listOf(
        iosX64(), iosArm64(), iosSimulatorArm64()
    ).map {
        it.compilations.forEach { compilation ->
            compilation.kotlinOptions.freeCompilerArgs += listOf("-linker-options", "-lsqlite3")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:runtime:$sqlDelightVersion")
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:android-driver:$sqlDelightVersion")
            }
        }
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
            dependencies {
                implementation("com.squareup.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
    }
}

android {
    namespace = "com.example.wbdtestapp.database"
    compileSdk = 33
}

sqldelight {
    database("AppDatabase") {
        packageName = "com.example.wbdtestapp.database"
    }
    linkSqlite = true
}
pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "WBDTestApp"
include(":androidApp")
include(":shared")
include(":network")
include(":model")
include(":database")

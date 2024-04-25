@Suppress("MemberVisibilityCanBePrivate")

object AppInfo {
    const val APPLICATION_ID = "org.blubz.fitsync"

    // Manifest version information
    object Version {
        const val CODE = 1006
        val NAME = "1.0.0 ($CODE.${System.getenv("BUILD_NUMBER")})"
    }

    object AndroidSdk {
        const val MIN = 21
        const val COMPILE = 34
        const val TARGET = 34
    }
}
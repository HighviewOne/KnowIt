plugins {
    alias(libs.plugins.android.application) apply false
    // AGP 9 has built-in Kotlin, so :app doesn't apply this plugin; declaring it here
    // pins the Kotlin Gradle plugin to libs.versions.kotlin instead of AGP's bundled version.
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
}

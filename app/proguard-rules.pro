# Add project specific ProGuard rules here.
# For more details, see:
#   http://developer.android.com/guide/developing/tools/proguard.html

# Kotlin
-keepattributes *Annotation*
-keepclassmembers class ** {
    @kotlin.jvm.JvmStatic *;
}

# Compose
-keep class androidx.compose.** { *; }
-dontwarn androidx.compose.**

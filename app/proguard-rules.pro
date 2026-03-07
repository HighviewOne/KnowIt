# Add project specific ProGuard rules here.
# For more details, see:
#   http://developer.android.com/guide/developing/tools/proguard.html

# Keep all Kotlin annotations
-keepattributes *Annotation*

# Keep Kotlin companion objects
-keepclassmembers class ** {
    @kotlin.jvm.JvmStatic *;
}

# Compose - keep essential classes and interfaces
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.foundation.** { *; }
-keep class androidx.compose.material3.** { *; }
-keep class androidx.compose.ui.** { *; }

# Lifecycle and saved state
-keep class androidx.lifecycle.** { *; }
-keep class androidx.savedstate.** { *; }

# DataStore
-keep class androidx.datastore.preferences.** { *; }

# Remove logging in release builds
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}


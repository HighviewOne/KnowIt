# Project-specific R8 rules.
#
# Compose, Lifecycle, DataStore, and coroutines ship their own consumer rules,
# so no blanket -keep rules are needed here; adding them stops R8 from
# shrinking those libraries. Only add a rule when a release build proves it's needed.

# Remove verbose/debug/info logging in release builds
-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
    public static *** i(...);
}

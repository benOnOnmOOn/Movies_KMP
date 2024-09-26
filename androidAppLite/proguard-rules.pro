# Remove all standard Android logging invocations.
-assumenosideeffects class android.util.Log { *; }

-assumenosideeffects class java.util.logging.** { *; }
-assumenosideeffects class org.slf4j.** { *; }

# Remove all printing of stack traces.
-assumenosideeffects class java.lang.Throwable {
    public void printStackTrace();
}

-repackageclasses ''

-allowaccessmodification

# Add rule that keep ktor url params
# https://youtrack.jetbrains.com/issue/KTOR-5564/Request-parameters-encodes-to-empty-string-with-Android-R8-for-release-build-after-update-kotlin-language-version-up-to-1.8.10
-keepclassmembers class io.ktor.http.CodecsKt$encodeURL** { *; }

-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
  public static void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
  public static void checkFieldIsNotNull(java.lang.Object, java.lang.String);
  public static void checkFieldIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
  public static void checkNotNull(java.lang.Object);
  public static void checkNotNull(java.lang.Object, java.lang.String);
  public static void checkNotNullExpressionValue(java.lang.Object, java.lang.String);
  public static void checkNotNullParameter(java.lang.Object, java.lang.String);
  public static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
  public static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String);
  public static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
  public static void throwUninitializedPropertyAccessException(java.lang.String);
}

# It was removed by excluding androidx.vectordrawable dep in main build gradle
-dontwarn androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
-dontwarn androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

-dontwarn org.slf4j.Logger
-dontwarn org.slf4j.LoggerFactory

# Valid rules copy paste from file "proguard-android-optimize.txt"

# For native methods, see https://www.guardsquare.com/manual/configuration/examples#native
-keepclasseswithmembernames,includedescriptorclasses class * {
    native <methods>;
}

# For enumeration classes, see https://www.guardsquare.com/manual/configuration/examples#enumerations
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
    public static final ** CREATOR;
}


-keep public class * {
    public protected *;
}

-assumenosideeffects class android.util.Log { *; }
-assumenosideeffects class java.util.logging.** { *; }
-assumenosideeffects class org.slf4j.** { *; }
-assumenosideeffects class kotlin.DeprecationLevel { *; }
-assumenosideeffects class kotlin.Deprecated { *; }
-assumenosideeffects class kotlin.ReplaceWith { *; }

# Remove all printing of stack traces.
-assumenosideeffects class java.lang.Throwable {
    public void printStackTrace();
}

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

# Repackage all obfuscated classes to packege specific for this module
-repackageclasses com.bz.movies.kmp.network

-dontwarn java.lang.invoke.StringConcatFactory

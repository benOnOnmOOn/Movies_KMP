
plugins {
    alias(libs.plugins.movies.binary.compatibility)
    alias(libs.plugins.movies.dependency.analysis)
    alias(libs.plugins.movies.kmp.library)
//    alias(libs.plugins.movies.jvm.library)
    alias(libs.plugins.movies.android.lint)
    alias(libs.plugins.movies.ktlint)
    alias(libs.plugins.movies.kover)
    alias(libs.plugins.movies.strict.dependencies)
}


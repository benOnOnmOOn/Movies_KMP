package com.bz.movies

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

/**
 * Configure Compose-specific options
 */
internal fun Project.configureAndroidCompose(commonExtension: CommonExtension<*, *, *, *, *, *>) {
    commonExtension.buildFeatures { compose = true }
}

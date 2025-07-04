package com.bz.movies

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun Project.getBooleanProperty(
    name: String,
    default: Boolean,
): Boolean = providers.gradleProperty(name).orNull?.toBooleanStrictOrNull() ?: default

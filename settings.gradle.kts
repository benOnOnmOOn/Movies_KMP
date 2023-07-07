import java.net.URI

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com.google.*")
            }
        }
        gradlePluginPortal()
    }
}

plugins {
    id("com.gradle.enterprise") version ("3.13.1")
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        google {
            content {
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com.google.*")
            }
        }
        maven {
            url = URI.create("https://androidx.dev/storage/compose-compiler/repository/")
            content {
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}

rootProject.name = "Movies_KMP"
include(":androidApp")
include(":android:presentation:core")
include(":android:presentation:screens")
include(":data:network")
include(":data:dto")
include(":data:database")

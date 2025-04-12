pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com.google.*")
                includeGroupByRegex("org.chromium.net.*")
                excludeGroup("com.google.auto.service")
            }
        }
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    @Suppress("UnstableApiUsage")
    repositories {
        google {
            content {
                includeGroupByRegex("com.android.*")
                includeGroupByRegex("com.google.*")
                includeGroupByRegex("androidx.*")
                excludeGroup("com.google.auto.service")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":convention")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
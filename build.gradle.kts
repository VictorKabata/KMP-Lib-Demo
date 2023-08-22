plugins {
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.kotlin) apply false
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.multiplatform) apply false
    alias(libs.plugins.jvm) apply false
    alias(libs.plugins.nativeCocoapod) apply false

    alias(libs.plugins.detekt)
    alias(libs.plugins.spotless)
}

subprojects {
    apply(plugin = "io.gitlab.arturbosch.detekt")
    detekt {
        parallel = true
        config = files("${project.rootDir}/config/detekt/detekt.yml")
    }

    apply(plugin = "com.diffplug.spotless")
    spotless {
        kotlin {
            target("**/*.kt")
            licenseHeaderFile(
                rootProject.file("${project.rootDir}/spotless/copyright.kt"),
                "^(package|object|import|interface)"
            )
        }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

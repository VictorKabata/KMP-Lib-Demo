plugins {
    alias(libs.plugins.nativeCocoapod)
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)

    /*id("maven-publish")
    id("signing")
    id("co.touchlab.faktory.kmmbridge") version "0.3.7"*/
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    android {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_1_8.toString()
            }
            publishLibraryVariants("release", "debug")
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    // js()

    cocoapods {
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "bridge"
            isStatic = true
        }
    }

    sourceSets {
        sourceSets["commonMain"].dependencies {
            //put your multiplatform dependencies here
        }

        sourceSets["commonTest"].dependencies {
            implementation(kotlin("test"))

        }

        sourceSets["androidMain"].dependencies {}

        sourceSets["androidTest"].dependencies {}

        sourceSets["iosMain"].dependencies {}

        sourceSets["iosTest"].dependencies {}

        sourceSets["jvmMain"].dependencies {}

        sourceSets["jvmTest"].dependencies {}
    }
}

android {
    namespace = "com.vickbt.bridge"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
}

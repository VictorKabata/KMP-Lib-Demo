plugins {
    alias(libs.plugins.nativeCocoapod)
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)

    alias(libs.plugins.dokka)
    alias(libs.plugins.kover)

    id("maven-publish")
    // id("signing")

    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
}

kotlin {

    android {
        publishLibraryVariants("release", "debug")
    }

    ios()

    jvm()

    // js()

    cocoapods {
        summary = "Kotlin multiplatform lib as a Swift package demo"
        homepage = "https://github.com/VictorKabata/KMP-Lib-Demo"
        version = "1.0"
        ios.deploymentTarget = "14.1"
        framework {
            baseName = "KmpLibDemo"
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

    /*publishing {
        singleVariant("release") {
            // withSourcesJar()
            withJavadocJar()
        }
    }*/
}

/**Publish android AAR lib*/
afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                groupId = "com.github.VictorKabata"
                artifactId = "kmp-lib-demo"
                version = "0.0.1"

                from(components["release"])
            }
        }
    }
}

/**Publish iOS SPM lib*/
multiplatformSwiftPackage {
    packageName("KmpLibDemo")
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("14.1") }
    }
}



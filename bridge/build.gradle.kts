plugins {
    alias(libs.plugins.nativeCocoapod)
    alias(libs.plugins.multiplatform)
    alias(libs.plugins.android.library)

    alias(libs.plugins.dokka)
    alias(libs.plugins.kover)

    /*id("maven-publish")
    id("signing")
    id("co.touchlab.faktory.kmmbridge") version "0.3.7"*/
}

val dokkaOutputDir = buildDir.resolve("reports/dokka")

val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")

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

tasks.dokkaHtml.configure {
    outputDirectory.set(dokkaOutputDir)
}

val deleteDokkaOutputDir by tasks.register<Delete>("deleteDokkaOutputDirectory") {
    delete(dokkaOutputDir)
}

val javadocJar = tasks.register<Jar>("javadocJar") {
    dependsOn(deleteDokkaOutputDir, tasks.dokkaHtml)
    archiveClassifier.set("javadoc")
    from(dokkaOutputDir)
}

/*
publishing {

    repositories {
        maven {
            name = "Sonatype"
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl
            else releasesRepoUrl

            credentials {
                username = project.get("OSSRH_USERNAME")
                password = project.get("OSSRH_PASSWORD")
            }
        }
    }

    publications.withType<MavenPublication> {

        artifact(javadocJar)

        pom {
            groupId = project.get("POM_GROUPID")
            artifactId = project.get("POM_ARTIFACTID")
            version = project.get("POM_VERSION")

            name.set(project.get("POM_NAME"))
            description.set(project.get("POM_DESCRIPTION"))
            url.set(project.get("POM_URL"))

            developers {
                developer {
                    id.set(project.get("POM_DEVELOPER_ID"))
                    name.set(project.get("POM_DEVELOPER_NAME"))
                    email.set(project.get("POM_DEVELOPER_EMAIL"))
                }
            }

            licenses {
                license {
                    name.set(project.get("POM_LICENSE_NAME"))
                    url.set(project.get("POM_LICENSE_URL"))
                }
            }

            issueManagement {
                system.set(project.get("POM_ISSUE_SYSTEM"))
                url.set(project.get("POM_ISSUE_URL"))
            }

            scm {
                connection.set(project.get("POM_SCM_CONNECTION"))
                developerConnection.set(project.get("POM_SCM_DEVELOPER_CONNECTION"))
                url.set(project.get("POM_SCM_URL"))
            }
        }
    }

    signing {
        val signingKeyId = project.get("SIGNING_ID")
        val signingKeyPassword = project.get("SIGNING_KEY")
        val signingKey = project.get("SIGNING_PASSWORD")

        useInMemoryPgpKeys(
            signingKeyId, signingKeyPassword, signingKey
        )
        sign(publishing.publications)
    }
}
*/

/*addGithubPackagesRepository()
kmmbridge {
    frameworkName.set("DarajaMultiplatform")
    mavenPublishArtifacts()
    githubReleaseVersions()
    versionPrefix.set("0.2")
    spm()
}*/

// Opt-In Experimental ObjCName in Kotlin > 1.8.0
kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}

task("testClasses").doLast {
    println("This is a dummy testClasses task")
}

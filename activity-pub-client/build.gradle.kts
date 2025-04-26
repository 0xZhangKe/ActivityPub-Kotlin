
plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
    id("com.vanniktech.maven.publish")
    alias(libs.plugins.dokka)
}

android {
    namespace = "com.zhangke.activitypub.client"
    compileSdk = 34
    defaultConfig {
        minSdk = 21
        consumerProguardFiles("consumer-rules.pro")
    }
}

kotlin {
    jvm()
    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()
    sourceSets {
        commonMain {
            dependencies {
                implementation(libs.kotlinx.serialization.json)

                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.ktor.client.serialization.kotlinx.json)

                implementation(libs.ktor.client.core)
                implementation(libs.ktorfit.lib)
                implementation(libs.ktorfit.converters.response)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}

tasks.named("sourcesJar") {
    dependsOn(tasks.named("kspCommonMainKotlinMetadata"))
}
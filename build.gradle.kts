plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
}

group = "com.zhangke.activitypub"
version = "1.0.0-SNAPSHOT"

kotlin {
    jvm()
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

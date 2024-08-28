plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinx.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktorfit)
}

group = "com.zhangke.activitypub"
version = "1.0.0-SNAPSHOT"

dependencies {
    testImplementation("junit:junit:4.+")
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    implementation(libs.bundles.kotlin)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging)

    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.client.serialization.kotlinx.json)
    implementation(libs.ktor.client.okhttp)

    implementation(libs.ktorfit.lib)
    implementation(libs.ktorfit.converters.response)
}

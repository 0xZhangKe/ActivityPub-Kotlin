plugins {
    id("utopia.jvm.library")
}

group = "com.zhangke.activitypub"
version = "1.0.0-SNAPSHOT"

dependencies {
    testImplementation("junit:junit:4.+")
    testImplementation("org.jetbrains.kotlin:kotlin-test")

    implementation(libs.bundles.kotlin)

    implementation(libs.okhttp3)
    implementation(libs.okhttp3.logging)
    implementation(libs.retrofit2)
    implementation(libs.retrofit2.converter.gson)
    implementation(libs.gson)
}

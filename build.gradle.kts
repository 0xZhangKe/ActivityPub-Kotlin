import org.jetbrains.kotlin.gradle.dsl.KotlinVersion

plugins {
    id("utopia.jvm.library")
}

group = "com.zhangke.activitypub"
version = "1.0.0-SNAPSHOT"

kotlin{
    compilerOptions{
        languageVersion.set(KotlinVersion.KOTLIN_2_0)
        freeCompilerArgs.add("-XXLanguage:+ExplicitBackingFields")
    }
}

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

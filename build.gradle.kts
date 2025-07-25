import com.vanniktech.maven.publish.SonatypeHost

plugins {
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktorfit) apply false
    alias(libs.plugins.publish)
    alias(libs.plugins.dokka)
}

allprojects {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = "1.8"
        }
    }
}

allprojects {
    group = "io.github.0xzhangke"
    version = ProjectVersion.VERSION

    plugins.withId("com.vanniktech.maven.publish.base") {
        mavenPublishing {
            publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL, automaticRelease = true)
            signAllPublications()
            pom {
                name.set("ActivityPubClient")
                description.set("Kotlin version of the ActivityPub protocol client SDK.")
                url.set("https://github.com/0xZhangKe/ActivityPub-Kotlin")
                licenses {
                    license {
                        name.set("Apache 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0")
                        distribution.set("repo")
                    }
                }
                developers {
                    developer {
                        id.set("zhangke")
                        name.set("Zhangke")
                        url.set("https://github.com/0xZhangKe")
                    }
                }
                scm {
                    url.set("https://github.com/0xZhangKe/ActivityPub-Kotlin")
                    connection.set("scm:git:git://github.com:0xZhangKe/ActivityPub-Kotlin.git")
                    developerConnection.set("scm:git:git://github.com:0xZhangKe/ActivityPub-Kotlin.git")
                }
            }
        }
    }

    // fix order of android release lint tasks
    listOf(
        "generateReleaseLintVitalModel",
        "lintVitalAnalyzeRelease",
    ).forEach { name ->
        tasks.matching { it.name == name }.configureEach {
            dependsOn(tasks.matching { it.name == "copyFontsToAndroidAssets" })
        }
    }
}

object ProjectVersion {
    // incompatible API changes
    private const val MAJOR = "0"

    // functionality in a backwards compatible manner
    private const val MINOR = "1"

    // backwards compatible bug fixes
    private const val PATH = "3"
    const val VERSION = "$MAJOR.$MINOR.$PATH"
}

tasks.dokkaHtmlMultiModule {
    moduleVersion.set(ProjectVersion.VERSION)
    outputDirectory.set(rootDir.resolve("docs/static/api"))
}

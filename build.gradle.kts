plugins {
    `java-library`
    idea
    id("io.github.gradle-nexus.publish-plugin") version "1.0.0"
}

description = "Robocode - Build the best - destroy the rest!"

val ossrhUsername: String by project
val ossrhPassword: String by project

subprojects {
    apply(plugin = "java")

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(8))
        }
    }
}

nexusPublishing {
    repositories {
        sonatype {
            nexusUrl.set(uri("https://oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://oss.sonatype.org/content/repositories/snapshots/"))
            stagingProfileId.set("c7f511545ccf8")
            username.set(ossrhUsername)
            password.set(ossrhPassword)
        }
    }
}

val initializeSonatypeStagingRepository by tasks.existing
subprojects {
    initializeSonatypeStagingRepository {
        shouldRunAfter(tasks.withType<Sign>())
    }
}
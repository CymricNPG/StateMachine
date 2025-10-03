plugins {
    id("java")
    `maven-publish`
    id("signing")
}

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
    withSourcesJar()
    withJavadocJar()
}

dependencies {
    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.jupiter)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("-javaagent:${configurations.testRuntimeClasspath.get().single { it.name.contains("byte-buddy-agent") }.absolutePath}")
}

// Javadoc options

tasks.withType<Javadoc>().configureEach {
    (options as StandardJavadocDocletOptions).apply {
        links("https://docs.oracle.com/en/java/javase/25/docs/api/")
    }
    (options as StandardJavadocDocletOptions).apply {
        addStringOption("Xdoclint:-missing", "-quiet")
    }

}
val license = "GNU LESSER GENERAL PUBLIC LICENSE"
val licenseUrl = "https://www.gnu.org/licenses/lgpl-3.0.en.html"

tasks.withType<Jar>().configureEach {
    manifest {
        attributes(
            "Manifest-Version" to "1.0",
            "Created-By" to "Gradle ${gradle.gradleVersion}",
            "Built-By" to System.getProperty("user.name"),
            "Build-Jdk" to System.getProperty("java.version"),
            "Implementation-Title" to project.name,
            "Implementation-Version" to project.version,
            "Implementation-Vendor" to "net.npg",
            "Implementation-Vendor-Id" to "net.npg",
            "Implementation-URL" to "https://github.com/you/your-repo",
            "License" to license,
            "License-URL" to licenseUrl
        )
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])

            groupId = "net.npg"
            artifactId = "your-artifact-id" // Replace with your artifact ID
            version = "1.0.0-SNAPSHOT"

            pom {
                name.set("Your Library Name")
                description.set("Description of your library")
                url.set("https://yourproject.com")

                licenses {
                    license {
                        name.set(license)
                        url.set(licenseUrl)
                    }
                }

                developers {
                    developer {
                        id.set("your-id")
                        name.set("Your Name")
                        email.set("you@example.com")
                    }
                }

                scm {
                    connection.set("scm:git:git://github.com/you/your-repo.git")
                    developerConnection.set("scm:git:ssh://github.com:you/your-repo.git")
                    url.set("https://github.com/you/your-repo")
                }
            }
        }
    }

    repositories {
        maven {
            val releasesRepoUrl = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            val snapshotsRepoUrl = uri("https://s01.oss.sonatype.org/content/repositories/snapshots/")
            url = if (version.toString().endsWith("SNAPSHOT")) snapshotsRepoUrl else releasesRepoUrl
            credentials {
                username = System.getenv("OSSRH_USERNAME")
                password = System.getenv("OSSRH_PASSWORD")
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
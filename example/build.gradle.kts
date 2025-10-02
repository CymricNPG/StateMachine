plugins {
    id("java")
}

group = "net.npg"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

dependencies {
    implementation(project(":base"))

    testImplementation(platform(libs.junit.bom))
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.jupiter)
}

tasks.test {
    useJUnitPlatform()
    jvmArgs("-javaagent:${configurations.testRuntimeClasspath.get().single { it.name.contains("byte-buddy-agent") }.absolutePath}")
}
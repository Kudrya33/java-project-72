plugins {
    id("java")
    id("com.github.ben-manes.versions") version "0.52.0"
    application
    checkstyle
    jacoco
    id("io.freefair.lombok") version "8.12.1"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("se.patrikerdes.use-latest-versions") version "0.2.18"
    id("org.sonarqube") version "6.0.1.5171"
}

sonar {
    properties {
        property("sonar.projectKey", "Kudrya33_java-project-72")
        property("sonar.organization", "pavel-kudrya")
        property("sonar.host.url", "https://sonarcloud.io")
    }
}

application {
    mainClass.set("hexlet.code.App")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.konghq:unirest-java-bom:4.4.5")
    implementation("com.konghq:unirest-java-core:4.4.5")
    implementation("com.konghq:unirest-modules-gson:4.4.5")
    implementation("com.konghq:unirest-modules-jackson:4.4.5")
    implementation("org.postgresql:postgresql:42.7.3")
    implementation ("org.jsoup:jsoup:1.18.3")
    implementation("com.h2database:h2:2.3.232")
    implementation("com.zaxxer:HikariCP:6.2.1")
    implementation("gg.jte:jte:3.1.16")
    implementation("io.javalin:javalin:6.4.0")
    implementation("io.javalin:javalin-rendering:6.4.0")
    implementation("io.javalin:javalin-bundle:6.4.0")
    implementation("org.slf4j:slf4j-simple:2.0.17")
    implementation("net.datafaker:datafaker:2.4.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.18.2")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
    testImplementation("org.assertj:assertj-core:3.25.3")
    testImplementation(platform("org.junit:junit-bom:5.10.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    reports { xml.required.set(true) }
}

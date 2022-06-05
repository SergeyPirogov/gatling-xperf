
plugins {
    java
    id("com.palantir.graal") version "0.10.0"
}

group = "io.githib.itishniki"
version = "1.0-SNAPSHOT"

description = "Github Actions Java CMD"

graal {
    mainClass("io.reqover.ReqoverCli")
    outputName("reqover")

    option("-H:EnableURLProtocols=http,https")
    option("-H:+AddAllCharsets")

    option("--enable-all-security-services")
    option("--allow-incomplete-classpath")
    option("--no-fallback")
    javaVersion("11")
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("info.picocli:picocli-codegen:4.1.4")

    implementation("info.picocli:picocli:4.1.4")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks {
    test {
        useJUnitPlatform()
    }
}
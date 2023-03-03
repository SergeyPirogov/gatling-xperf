
plugins {
    java
    application
    id("com.palantir.graal") version "0.10.0"
}

group = "io.perf.report"
version = "1.0"

description = "Github Actions Java CMD"

graal {
    mainClass("io.perf.report.PerfReportCli")
    outputName("perf_report")

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
    implementation("net.quux00.simplecsv:simplecsv:2.0")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("log4j:log4j:1.2.17")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.7.0")
}

tasks {
    test {
        useJUnitPlatform()
    }
}

application {
    mainClass.set("io.perf.report.PerfReportCli")
}
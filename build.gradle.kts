plugins {
    java
    id("com.palantir.graal") version "0.9.0"
}

group = "io.githib.itishniki"
version = "1.0-SNAPSHOT"

description = "Github Actions Java CMD"

graal {
    mainClass("io.github.itishniki.javacmd.SearchForMe")
    outputName("search-for-me")
}

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("info.picocli:picocli-codegen:4.1.4")

    implementation("org.seleniumhq.selenium:selenium-java:3.141.59")
    implementation("info.picocli:picocli:4.1.4")
}

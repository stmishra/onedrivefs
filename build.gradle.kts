import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

group = "com.github.stmishra"
version = "1.0-SNAPSHOT"

buildscript {
    var kotlin_version: String by extra
    kotlin_version = "1.2.51"

    repositories {
        mavenCentral()
        jcenter()
    }
    
    dependencies {
        classpath(kotlinModule("gradle-plugin", kotlin_version))
    }
    
}

apply {
    plugin("kotlin")
}

val kotlin_version: String by extra

repositories {
    mavenCentral()
    jcenter()
    maven { setUrl( "https://jitpack.io") }

}

dependencies {
    compile(kotlinModule("stdlib-jdk8", kotlin_version))
    compile("com.github.fge:java7-fs-base:0.0.1")
    compile("khttp:khttp:0.1.0")
    compile("com.github.scribejava:scribejava-apis:5.4.0")
    testCompile("org.mockito:mockito-core:2.8.47")
    testCompile("org.junit.jupiter:junit-jupiter-api:5.2.0")
    testRuntime("org.junit.platform:junit-platform-launcher:1.2.0")
    testRuntime("org.junit.jupiter:junit-jupiter-engine:5.2.0")
    testRuntime("org.junit.vintage:junit-vintage-engine:5.2.0")


}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}


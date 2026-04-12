plugins {
    java
}

group = "de.theniclas"
version = "2.0.0"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    compileOnly(libs.paper)
}

val targetJavaVersion = 17
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(targetJavaVersion)
    options.encoding = "UTF-8"
}

// The source tree uses a non-standard layout inherited from the Maven build:
//   src/de/theniclas/...  →  Java sources
//   src/main/resources/   →  resources (plugin.yml, etc.)
sourceSets {
    main {
        java {
            setSrcDirs(listOf("src"))
            exclude("main/**")
        }
        resources {
            setSrcDirs(listOf("src/main/resources"))
        }
    }
}

tasks.jar {
    archiveFileName.set("BauserverPlugin.jar")
    destinationDirectory.set(rootDir)
}

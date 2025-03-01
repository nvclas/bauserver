import io.papermc.paperweight.userdev.ReobfArtifactConfiguration

plugins {
    java
    alias(libs.plugins.runPaper)
    alias(libs.plugins.paperweight.userdev)
}

group = "de.nvclas"
version = "2.0"

repositories {
    mavenCentral()
    maven {
        name = "papermc-repo"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    implementation(libs.annotations)
    implementation(libs.luckperms)
    paperweight.paperDevBundle(libs.versions.paper)
    compileOnly(libs.lombok)
    annotationProcessor(libs.lombok)
}

paperweight {
    addServerDependencyTo = configurations.named(JavaPlugin.COMPILE_ONLY_CONFIGURATION_NAME).map { setOf(it) }
}

val targetJavaVersion = 21
java {
    val javaVersion = JavaVersion.toVersion(targetJavaVersion)
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
    if (JavaVersion.current() < javaVersion) {
        toolchain.languageVersion.set(JavaLanguageVersion.of(targetJavaVersion))
    }
}

tasks.withType<JavaCompile>().configureEach {
    if (targetJavaVersion >= 10 || JavaVersion.current().isJava10Compatible) {
        options.release.set(targetJavaVersion)
        options.encoding = "UTF-8"
    }
}

tasks {
    runServer {
        minecraftVersion("1.21.1")
    }
}

tasks.processResources {
    val props = mapOf("version" to version)
    inputs.properties(props)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") {
        expand(props)
    }
}

paperweight.reobfArtifactConfiguration = ReobfArtifactConfiguration.MOJANG_PRODUCTION
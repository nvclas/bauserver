plugins {
    alias(libs.plugins.runPaper)
    alias(libs.plugins.paperweight.userdev)
}

version = 2.1

dependencies {
    paperweight.paperDevBundle(libs.versions.paper)
}

tasks {
    runServer {
        minecraftVersion("1.21.1")
    }
}
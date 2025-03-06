rootProject.name = "TokyoBuild"

include("BauserverPlugin", "Levels", "AntiBlockUpdate")
project(":BauserverPlugin").projectDir = file("plugins/BauserverPlugin")
project(":Levels").projectDir = file("plugins/Levels")
project(":AntiBlockUpdate").projectDir = file("plugins/AntiBlockUpdate")
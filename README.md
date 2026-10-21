# GameTime

A small Fabric client mod for Minecraft **1.20.1**. Press **G** or run **/gametime** to open the control panel.

## Features
- Set sunrise, noon, sunset, and midnight
- Freeze or resume the daylight cycle
- Set clear, rain, or thunder weather
- Reset the daylight cycle and time

## Build
The GitHub Actions workflow builds the mod automatically on each push. Open the repository's **Actions** tab, select the successful run, then download the `gametime-fabric-1.20.1` artifact. The downloaded artifact is a ZIP containing the mod JAR.

## Important
These controls send normal Minecraft commands. They work in singleplayer and on servers where your player has permission to use the commands. They do not bypass server permissions or force time/weather changes on multiplayer servers.

This first build targets Minecraft 1.20.1 with Fabric and Java 17. Other Minecraft versions need separate compatibility builds.

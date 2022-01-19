# Gemohn3DEngine
Yet another 3D engine which uses legacy LWJGL APIs.

This engine includes some template configuration files, and an internal shell language allowing for conveinient usage of OpenGL commands (via the LWJGL binding). There are also some other informational files containing the details of an originating OS's environment, so in the event your OS is missing critical libraries, there are 'hints' you can follow.

On Linux:
1. you'll want to cd into this project, then into:
./lwjglImport2/porting20211219/
2. execute the main build and execution script:
./compileandRun20211219.bsh
3. under the default configuration (while main graphical window is selected) 
WASD for viewport/camera movement
QE for viewport/camera vertical movement
ZX for change viewport movement step rate (how much each key event "moves" the camera/viewport)


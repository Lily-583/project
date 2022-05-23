1. SDK Tools Required: Google Play services (version 49) select Tools-SDK Manager-SDK-Tools
2. Every time before you launch the app, you need to set your location on the emulator manually to USC. (Click the 3 dots above the emulator and select location).
3. Emulator: Pixel 3 API 30
4. We have a sample user. Username: Leo; Password: 1223 You may examine the order history and profile function with this user. 
We also have a sample merchant. Username: Boba God  Password:123
5. Change the path of the jar file with the actual path of the jar file on your local computer in build.gradle
6. Please make sure to run all tests thoroughly without interruption. Many of them involve SQL operations and include self-cleaning features. Stopping halfway will lead to significant issues. 
7. When checking daily analysis, there might be nothing in there because no order has been placed on that day yet for that customer. 
8. Please set the emulator in developer mode.On your device, under Settings > Developer options, disable the following 3 settings: Window animation scale Transition animation scale Animator duration scale
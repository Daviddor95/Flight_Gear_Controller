# Flight Gear Controller
Welcome aboard! The Flight Gear Controller Android application allows you to connect from your mobile device to a computer running the Flight Gear application and control and fly the aircraft.

## Features
- Connect to Flight Gear application running on a desktop computer
- Control the aileron and elevator values of the aircraft using the joystick
- Control the throttle and rudder values of the aircraft using the sliders
- Lots and lots of fun

Moving the joystick up and down controls the elevator value of the aircraft, while moving the joystick left and right controls the aileron value of the aircraft. Moving the left slider up and down controls the throttle value of the aircraft, while moving the bottom slider left and right controls the rudder value of the aircraft.

![AppScreenshot](https://github.com/Ellakh/FlightGearJoystickApp/blob/master/ApplicationScreenshot.jpg)

## Getting Started
The Flight Gear Controller application requires the following components:
- [Flight Gear] - desktop application the simulates flights, aircraft and airports
- [Android Studio] - IDE for developing android applications
- [Android Gradle Plugin] - version 4.2.1
- [Gradle] - version 6.7.1
- [Android SDK] - device (physical or emulated) that runs Android SDK version 22 or above
- [Flight Gear Controller] - The Flight Gear Controller application itself

## Flight Gear Installation
Download the Flight Gear desktop application from the [Flight Gear website] and install it on the desired computer using the instructions on their website and the installation wizard.
After the installation completed, run the Flight Gear application and enter the settings tab. In the "Additional Settings" text box, enter the following:
```sh
--telnet=socket,in,10,127.0.0.1,6400,tcp
```

## Android Studio Installation
Download the Android Studio IDE from the [Android Studio website] and install it on the desired computer using the instructions on their website and the installation wizard.
If you wish to run the application on emulated android device on your PC, you'll need to download and install a device that using Android API level 22 or above (Pixel 2 for example).
If you wish to run the application on your android device (OnePlus 6T recommended), connect the desired device to your computer using a USB cable, click the "Troubleshoot Device Connections" in the top of the IDE and follow the instructions.

## Fly!
After tou completed the previous steps, it's time for some fun!
Launch the Flight Gear application on your computer, and click the "Fly!" tab. Wait for the simulator to load, and then, on the top toolbar press on your aircraft's name and click "Autostart". Then, launch the Android Studio IDE, open the project from this repository (using "git clone"), and click "Run". If you're using your Android device, make sure that the device connected properly to the computer using a USB cable. When the application starts on the device (Physical or emulated), fill the local IP address (make sure you're running the application using the emulator or a physical device that's connected to the same local network as your computer) of the computer running the Flight Gear application. To check your local IP address (on windows), launch the command prompt (search for "cmd" in the search box), type "ipconfig" and hit "enter". The IP address you'll need is the "IPv4 Address" under the "Wireless LAN adapter Wi-Fi" section. Enter port "6400" in the Flight Gear Controller application and press "connect". Now you're ready to fly! You can use the left slider to control the throttle, and the bottom one to control the rudder. Use the joystick to control the aileron and elevator.

## Development Information

The Flight Gear Controller application developed in Java using the Android Studio IDE. The application is based on the MVVM architecture and uses also the Active Object design pattern and the dependency injection principle using the Strategy design Pattern.

## MVVM architecture
### View
The View contains two components:
- activity_main.xml - contains the design of the application user interface in XML format
- MainActivity.java - contains the view-logic behind the user interface (the XML)

The text fields and the sliders are bound to the appropriate fields in the viewModel using Data Binding. The "connect" button sends command to the ViewModel (calls the connectToSimulator method that's in the ViewModel class) using OnClick listener.

### ViewModel
The ViewModel contains the ViewModel.java - the bridge between the View and the Model, in order that the View won't know about the Model and the Model won't know about the View.

### Model
The Model contains three components:
- Model.java - contains the business logic of the program, using the Object Adapter design pattern to run the task one by one by order in a separate thread than the main thread
- Joystick.java - contains the logic behind the movement of the joystick
- IChange.java - an functional interface, in order to set the action that will be made on Joystick move using the dependency injection principle and the Strategy design Pattern. There's lambda expression injected to the "onChange" field (of type IChange) of the Joystick in the MainActivity.java

## Want to use our joystick in your project?
You're welcome to do so! All you need to do is:
- Copy the Joystick.java class to your project
- Copy the IChange.java interface to your project
- Copy the following code to your View's XML file to insert the joystick's view to your View, and adjust the parameters based on your needs:
```sh
    <com.flightgearcontroller.Joystick
        android:id="@+id/myJoystick"
        android:layout_width="320dp"
        android:layout_height="320dp"
        android:layout_centerHorizontal="true" />
```
- From your MainActivity's onCreate function, enter a lambda expression into the onChange field of your joystick, for example:
```sh
Joystick coolJoystick = findViewById(R.id.myJoystick);
coolJoystick.onChange = (x, y) -> {
    myViewModel.setX(x);
    myViewModel.setY(y);
};
```

## UML
![UML](https://github.com/Ellakh/FlightGearJoystickApp/blob/master/UML.png)

## Video presentation
Coming soon.


## Credits
Project by David Dorfman.

[//]: #
   [Flight Gear]: <https://www.flightgear.org/>
   [Android Studio]: <https://developer.android.com/studio>
   [Android Studio website]: <https://developer.android.com/studio>
   [Flight Gear website]: <https://www.flightgear.org/>
   [Flight Gear Remote Joystick]: <https://github.com/Ellakh/FlightGearJoystickApp>
   [Gradle]: <https://gradle.org/>
   [Android Gradle Plugin]: <https://gradle.org/>
   [Android SDK]: <https://developer.android.com/studio>
   [Click here]: <https://youtu.be/W0z9mX4rGK8>

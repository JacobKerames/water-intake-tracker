# Water Intake Tracker

<img src="https://github.com/JacobKerames/water-intake-tracker/assets/108101472/3b93cf02-b042-4d42-b42f-6cc46db014d3" width="600" height="auto">

Water Intake Tracker is an Android application that assists users in monitoring their daily water consumption. The app uses the user's weight to suggest a daily recommended water intake. It's built using Java, XML, SQLite, and the Android SDK.

## Table of Contents

- [Features](#features)
- [Installation and Setup](#installation-and-setup)
- [Usage](#usage)
- [Application Design and Architecture](#application-design-and-architecture)
- [Data Management](#data-management)
- [Technologies](#technologies)

## Features

- **Home Fragment:** Displays the user's daily water intake in comparison to the recommended amount, presented in text format and as a horizontal progress bar. Users can log their water consumption in ounces.

- **Settings Fragment:** Users can enter their weight, used to calculate the recommended daily water intake. 

- **Data Export:** Users can export their consumption data as CSV files for analysis or use in other health applications.

- **Information Fragment:** Provides useful information about the importance and benefits of drinking water.

## Installation and Setup

Follow these steps to set up the Water Intake Tracker app on your local machine for development:

1. **Clone the repository.**
   Open your terminal and use the command below:

   ```bash
   git clone https://github.com/JacobKerames/water-intake-tracker.git

2. Set up your Android development environment.
You should have [Android Studio](https://developer.android.com/studio?gclid=CjwKCAjw04yjBhApEiwAJcvNoVK_4pHEFiiwiLFCVPnMeXsTOF2pQv5gj3dAW7dDaQ7IK08OePen3xoC1RgQAvD_BwE&gclsrc=aw.ds) installed. Open the cloned project in Android Studio and let it build the project.

3. Run the app.
You can run the app on an emulator or a real device connected to your machine (API Level 33 - Tiramisu is recommended). Use the Run button in Android Studio to start the app.

## Usage
The Water Intake Tracker app is designed to be user-friendly and intuitive. Here's a quick guide to get you started:

1. **Enter your weight in the Settings Fragment.**
When you first open the app, navigate to the Settings Fragment and enter your weight. The app will use this to calculate your recommended daily water intake.

2. **Track your water intake in the Home Fragment.**
Navigate back to the Home Fragment. Here, you can log the amount of water you drink throughout the day. The app will display your progress towards your daily goal.

3. **Export your data.**
If you want to analyze your water consumption habits over time, you can export your data to a CSV file from the Settings Fragment.

4. **Learn more about hydration.**
Check out the Information Fragment for useful information about the importance of staying hydrated.

You can navigate between the different fragments using the navigation bar at the bottom of the screen.

## Application Design and Architecture

The application employs a main activity and three fragments to implement its functionality. The main activity, which includes a navigation host fragment, serves as the application's entry point. A navigation bar allows users to switch between the home, information, and settings fragments. View model classes are used for fragments with dynamic components to facilitate code readability and separation of concerns.

## Data Management

The application utilizes shared preferences, file output, and an SQLite database for data management. Shared preferences store the user's weight, which is used to calculate the recommended daily water intake. The SQLite database logs the user's daily water consumption. LiveData objects represent changing values in the user interface, such as the current weight setting and the home fragment's title.

## Technologies

- **Android Studio:** Provides comprehensive tools for app development.

- **Java:** Its object-oriented structure promotes efficient and readable code.

- **XML:** Facilitates customizable user interface design.

- **Android Jetpack Libraries:** Includes LiveData, ViewModel, and Navigation for enhanced data management, navigation, and view model structuring.

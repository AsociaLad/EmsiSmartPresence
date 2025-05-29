# EmsiSmartPresence

EmsiSmartPresence is an Android application designed to leverage smart presence detection using location services, Firebase, and modern Android UI components. This project is structured as a standard Gradle-based Android app.

## Features

- **Smart Presence Detection**: Uses Google Maps and Android Location services to determine user presence.
- **Firebase Integration**: Auth, Firestore, and Analytics are built-in.
- **Modern UI**: Utilizes Material Design, AppCompat, ConstraintLayout, GridLayout, and CardView.
- **Network Capabilities**: Includes OkHttp for HTTP requests and JSON handling.
- **Test Ready**: Configured for unit and instrumentation testing.

## Tech Stack

- **Language**: Kotlin
- **Min SDK**: 29
- **Target SDK**: 35
- **Build System**: Gradle (Kotlin DSL)

### Major Dependencies

- Google Maps (`play-services-maps`)
- Android Location (`play-services-location`)
- Firebase (Auth, Firestore, Analytics)
- OkHttp (networking)
- org.json (JSON parsing)
- Material Components & AndroidX

## Getting Started

1. **Clone the repository:**
   ```sh
   git clone https://github.com/AsociaLad/EmsiSmartPresence.git
   cd EmsiSmartPresence
   ```

2. **Open in Android Studio:**
   - Open the project folder.
   - Let Gradle sync and download dependencies.

3. **Configure Firebase:**
   - Add your `google-services.json` file to the `app/` directory for Firebase services.

4. **Build & Run:**
   - Select an emulator or connected device.
   - Click "Run".

## Project Structure

```
EmsiSmartPresence/
├── app/                # Main Android app module
├── build.gradle.kts    # Top-level Gradle config
├── gradle/             # Gradle wrapper files
├── gradlew, gradlew.bat
└── settings.gradle.kts
```

## Scripts

- `./gradlew build` - Build the project
- `./gradlew test` - Run unit tests

## Contributing

Contributions are welcome! Please open issues and pull requests.

## License

[Specify your license here, e.g., MIT, Apache 2.0, etc.]

---

**Note:** Remember to configure Firebase and Maps API keys before running the application.

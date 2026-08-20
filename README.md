# Balkan Radio App

A simple and elegant Android application for streaming Ex-Yu radio stations from Serbia, Bosnia, and Croatia.

## Features

- **Multi-Region Support:** Easily filter stations by region (Serbia, Bosnia, Croatia).
- **Search:** Fast search to find your favorite radio stations.
- **Media Playback:** High-quality audio streaming using Google's Media3 and ExoPlayer.
- **Background Playback:** Continue listening to your favorite music while using other apps.
- **Adaptive UI:** Modern Material 3 design that adapts to your device.
- **Easy Navigation:** Intuitive drawer navigation for switching between regions and settings.

## Security Architecture

The Balkan Radio App is built with a security-first mindset, adhering to 2026 Android standards:
- **Encrypted Storage:** All user preferences and favorite stations are stored using hardware-backed AES-256 encryption via `EncryptedSharedPreferences`.
- **Network Hardening:** Cleartext (HTTP) traffic is strictly disabled by default. A surgical whitelist in `network_security_config.xml` allows connections only to verified legacy radio domains.
- **Code Protection:** Release builds use R8 obfuscation and shrinking to protect against reverse engineering.
- **Privacy:** Android ADB backup is disabled to prevent unauthorized data extraction.

## Tech Stack

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose & XML (Hybrid)
- **Architecture:** MVVM (Model-View-ViewModel) pattern
- **Media:** Android Media3 (ExoPlayer & MediaSession)
- **Security:** EncryptedSharedPreferences (AES-256), Restricted Network Security Config
- **Performance:** Power Saving Mode, Optimized LoadControl

## Security Features (2026 Standards)

- **Data Encryption:** User preferences are stored using hardware-backed AES-256 encryption via `EncryptedSharedPreferences`.
- **Network Hardening:** Cleartext traffic is disabled globally and only allowed for specific legacy radio stream domains.
- **Code Protection:** Release builds are obfuscated and optimized using R8 to prevent reverse engineering.
- **Service Isolation:** Playback service is not exported to prevent unauthorized external access.

## Getting Started

### Prerequisites

- Android Studio Koala or newer
- Android SDK 24+ (Android 7.0 Nougat)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/radio-app.git
   ```
2. Open the project in Android Studio.
3. Build and run the app on your emulator or physical device.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

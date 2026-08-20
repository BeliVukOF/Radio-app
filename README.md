# Balkan Radio App

A simple and elegant Android application for streaming radio stations from across the Balkans and beyond.

## Features

- **Multi-Region Support:** Easily filter stations from 8 different countries.
- **Dynamic Favorites:** Smart system that keeps your favorite stations at the top and synchronized across the app.
- **Multi-Language UI:** Fully localized in 8 languages (Albanian, Bosnian, Bulgarian, Croatian, Greek, Macedonian, Montenegrin, and Serbian).
- **Modern Media Playback:** High-quality audio streaming using Android Media3, ExoPlayer, and MediaSession.
- **Background Playback:** Seamlessly continue listening while multitasking.
- **Power Saving Mode:** Optimize battery life by reducing animations and optimizing network buffering.
- **Adaptive UI:** Material 3 design with a global wooden map background and smooth transitions.

## Supported Regions

The app currently features over 50 hand-picked radio stations from:
- 🇦🇱 **Albania**
- 🇧🇦 **Bosnia and Herzegovina**
- 🇧🇬 **Bulgaria**
- 🇭🇷 **Croatia**
- 🇬🇷 **Greece**
- 🇲🇰 **North Macedonia**
- 🇲🇪 **Montenegro**
- 🇷🇸 **Serbia**

## Security Architecture

Built with a security-first mindset adhering to 2026 Android standards:
- **Encrypted Storage:** All user preferences and favorites are stored using hardware-backed AES-256 encryption via `EncryptedSharedPreferences`.
- **Network Hardening:** Cleartext (HTTP) traffic is strictly disabled by default. A surgical whitelist in `network_security_config.xml` allows connections only to verified legacy radio domains.
- **Code Protection:** Release builds use R8 obfuscation and shrinking to protect against reverse engineering.
- **Privacy:** Android ADB backup is disabled to prevent unauthorized data extraction.

## Tech Stack

- **Language:** Kotlin
- **UI Framework:** XML & ViewBinding (Modern Material 3)
- **Architecture:** MVVM (Model-View-ViewModel) pattern
- **Media:** Android Media3 (ExoPlayer & MediaSession)
- **Security:** Tink (Google's crypto library), EncryptedSharedPreferences
- **Navigation:** Jetpack Navigation Component

## Getting Started

### Prerequisites

- Android Studio Koala or newer
- Android SDK 24+ (Android 7.0 Nougat)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/BeliVukOF/Radio-app.git
   ```
2. Open the project in Android Studio.
3. Build and run the app on your emulator or physical device.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

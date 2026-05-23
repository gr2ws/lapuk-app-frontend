# LAPUK App Frontend

## Overview

Android frontend for **LAPUK: Waste Classification App**, developed as a CCS6 (Applications Development and Emerging Technologies) project. This app helps users classify waste using image-based detection, review saved results, and view supporting regional heatmap data through a clean mobile interface built with Jetpack Compose.

## Features

- Camera capture and gallery upload flow for waste images
- API integration for image detection (`POST /detect`)
- Saved detection history (image + metadata) in local app storage
- Detection statistics view (per waste category)
- Heatmap viewer powered by backend endpoint (`GET /render-heatmap`)
- Additional in-app pages: FAQs, Contact, About, Privacy Policy, Articles
- Portrait-optimized UI with custom navigation and onboarding-style presentation

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose, Material 3, Navigation Compose
- **Camera:** CameraX
- **Networking:** OkHttp, Retrofit, Gson
- **Build:** Gradle Kotlin DSL, Android Gradle Plugin 8.7.3

## Requirements

- Android Studio (recent stable)
- Android SDK (compileSdk 35, minSdk 24)

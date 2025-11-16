# VideoAppDemo


A modern Android video streaming application built with Jetpack Compose, Clean Architecture, Ktor for networking, and Koin for dependency injection.

## 🏗️ Project Structure

```
videoappdemo/
├── core/                           # Shared core components
│   ├── domain/                     # Domain layer
│   │   ├── DataError/              # Error handling
│   │   ├── Error/                  # Error types
│   │   └── Result.kt               # Result wrapper
│   ├── navigation/                 # App navigation
│   │   └── App.kt                  # Main navigation component
│   ├── network/                    # Network layer
│   │   ├── HttpClientFactory       # Ktor client factory
│   │   └── safeCall.kt             # Safe API call wrapper
│   └── di/                         # Dependency injection
│       └── Module.kt               # Koin modules
├── home/                           # Feature module
│   ├── data/                       # Data layer implementation
│   │   ├── mapper/                 # Data mappers
│   │   │   └── VideoItemMapper.kt  # Video data mapper
│   │   └── remote/                 # Remote data sources
│   │       ├── dto/                # Data Transfer Objects
│   │       │   └── VideoResponse.kt # API response models
│   │       └── repository/         # Repository implementations
│   │           ├── dataSource/     # Data source interfaces
│   │           │   └── VideoRemoteDataSource
│   │           └── dataSourceImpl/ # Data source implementations
│   │               └── VideoRemoteDataSourceImpl
│   └── domain/                     # Domain layer (use cases, entities)
│   └── presentation/               # UI layer (Compose screens, ViewModels)
└── app/                            # App module (entry point)

```

## 🛠️ Tech Stack

- UI: Jetpack Compose, Material Design 3

- Architecture: Clean Architecture, MVVM

- Networking: Ktor Client

- Dependency Injection: Koin

- Video Player: ExoPlayer

- Async Image Loading: Coil

- Navigation: Jetpack Navigation Compose

- Serialization: Kotlinx Serialization

## Features

- Video list screen with thumbnails

- Video player screen with ExoPlayer

- Clean Architecture separation

- Error handling

- Safe API calls

- Dependency Injection with Koin

- Modern Jetpack Compose UI


## 🏛️ Architecture

The app follows Clean Architecture with three main layers:

### Domain Layer

- Contains business logic and use cases

- Defines repository interfaces

- Includes domain models and error handling

### Data Layer

- Implements repository interfaces

- Handles API calls with Ktor

- Contains DTOs and mappers

- Manages data sources 

### Presentation Layer

- Jetpack Compose UI components

- ViewModels for state management

- Navigation between screens
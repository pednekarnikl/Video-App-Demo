# VideoAppDemo

https://github.com/user-attachments/assets/8d37931a-2159-4138-ad77-0df5ec425561

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


## 🔧 Setup Instructions

### Clone the repository

```git clone <repository-url>
cd videoappdemo
```

### Configure API Base URL
Update the base URL in HttpClientFactory or dependency injection module:

```
// In Module.kt or HttpClientFactory
baseUrl = "https://your-api-endpoint.com/"
```

### Build and Run

- Open project in Android Studio

- Build the project (Ctrl+F9)

- Run on device/emulator (Shift+F10)


## 🚀 Usage

### Video List Screen(Home)

- Displays a list of available videos

- Shows thumbnails, titles, and descriptions

- Click on any video to start playback

### Video Player Screen

- Full-featured video player using ExoPlayer

- Play/pause controls

- Seek bar for navigation


## 🔄 Data Flow

- UI → Calls ViewModel method

- ViewModel → Executes UseCase

- UseCase → Calls Repository

- Repository → Fetches from DataSource

- DataSource → Makes API call via Ktor

- Response → Mapped to Domain → UI State

## 🎨 UI Components

### Compose Screens

- Video list with LazyColumn

- Video card items with Coil images

- ExoPlayer integration for video playback

- Material Design 3 components

### State Management

- Uses StateFlow/State for reactive UI updates

- Loading, Success, Error states handling

- ViewModel scoping for configuration changes
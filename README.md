#VideoAppDemo


A modern Android video streaming application built with Jetpack Compose, Clean Architecture, Ktor for networking, and Koin for dependency injection.

##🏗️ Project Structure

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
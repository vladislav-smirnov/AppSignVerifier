# App Signature Verifier

**App Signature Verifier** is a utility library for Android that provides tools to verify various properties of applications, such as checking if an app is signed by the system, if it is a privileged app, or if it resides in the system partition. The library is designed following Clean Architecture principles to ensure modularity, testability, and maintainability.

---

## Features

- Check whether an app:
  - Is signed with a system signature.
  - Is a privileged app.
  - Resides in the system partition.
- Modular and extensible design.
- Built for modern Android development with Kotlin.

---

## Prerequisites

Before using this library, ensure your environment meets the following requirements:

- **Minimum Android SDK version**: 29
- **Target Android SDK version**: Latest stable version.

---

## Setup

To add this library to your Android project, follow these steps:

1. **Add the dependency to your `build.gradle` file:**

   ```gradle
   dependencies {
       implementation 'io.github.airdaydreamers:appsignverifier:xxx'
   }
   ```

2. **Sync your project with Gradle files.**

## Usage

This library offers simple, intuitive methods for verifying application properties. Detailed documentation and examples are available to help you integrate the library into your project seamlessly.

```kotlin
// Example usage
val isSystemApp = context.isSystemApp()
val isSignedBySystem = context.isSignedBySystem()
val isPrivileged = context.isPrivilegedApp()
val isInSystemPartition = context.isAppInSystemPartition()
```

---

## Design and Architecture

The project is structured to adhere to best practices in software design:

- **Clean Architecture**: Separation of concerns across distinct layers (e.g., data, domain, and presentation layers).
- **Modular Design**: Each component is self-contained and can be tested or extended independently.
- **Testability**: Fully supports unit testing with popular frameworks like Mockito and JUnit.

---

## Contributing

Contributions are welcome! Feel free to fork the repository, open issues, and submit pull requests if you'd like to contribute to the project!
Please make sure your code follows the project's guidelines and includes relevant documentation or tests.

---

Author
------
Vladislav Smirnov - @vladislav-smirnov on GitHub

## License

This project is licensed under the Apache License 2.0.  
See the [LICENSE](LICENSE) file for more details.



    Copyright © 2024 Vladislav Smirnov.
    DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
---
